package ru.mirea.lukutin.mirresproject.ui.hardware




import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import ru.mirea.lukutin.mirresproject.MainActivity
import ru.mirea.lukutin.mirresproject.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HardwareFragment : Fragment(), View.OnClickListener {
    val TAG = MainActivity::class.java.simpleName
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    // sensors
    private lateinit var listCountSensor: ListView
    private var sensorManager: SensorManager? = null
    private lateinit var takePhotoBut: Button

    // camera
    private lateinit var imageView: ImageView
    private var isWork = false
    private var imageUri: Uri? = null
    private lateinit var startRecBut: Button
    private lateinit var stopRecBut: Button
    private lateinit var startPlayBut: Button
    private lateinit var stopPlayBut: Button
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFile: File
    private lateinit var mParam1: String
    private lateinit var mParam2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1).toString()
            mParam2 = requireArguments().getString(ARG_PARAM2).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_hardware, container, false)
        takePhotoBut = view.findViewById(R.id.takePhotoButton)
        startRecBut = view.findViewById(R.id.startRecBut)
        stopRecBut = view.findViewById(R.id.stopRecBut)
        startPlayBut = view.findViewById(R.id.startPlayBut)
        stopPlayBut = view.findViewById(R.id.stopPlayBut)

        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        isWork = hasPermissions(view.context, *PERMISSIONS)
        if (!isWork) {
            ActivityCompat.requestPermissions(
                requireActivity(), PERMISSIONS,
                REQUEST_CODE_PERMISSION
            )
        }
        mediaRecorder = MediaRecorder()

        // инициализация кнопок
        takePhotoBut.setOnClickListener(this)
        startRecBut.setOnClickListener(this)
        stopRecBut.setOnClickListener(this)
        startPlayBut.setOnClickListener(this)
        stopPlayBut.setOnClickListener(this)
        imageView = view.findViewById(R.id.userPhoto)
        //проверка на наличие разрешений на использование камеры и запись в память
        val cameraPermissionStatus =
            ContextCompat.checkSelfPermission(view.context, Manifest.permission.CAMERA)
        val state = Environment.getExternalStorageState()
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            Log.d(TAG, "sd-card success")
            isWork = true
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )
        }


        // list sensors
        listCountSensor = view.findViewById<View>(R.id.listView) as ListView
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors: List<Sensor> = sensorManager!!.getSensorList(Sensor.TYPE_ALL)
        val arrayList: ArrayList<HashMap<String?, Any?>> = ArrayList()
        var sensorTypeList: HashMap<String?, Any?>
        for (i in 0..2) {
            sensorTypeList = HashMap()
            sensorTypeList["Name"] = sensors[i].getName()
            sensorTypeList["Value"] = sensors[i].getMaximumRange()
            arrayList.add(sensorTypeList)
        }
        val mHistory = SimpleAdapter(
            view.context,
            arrayList,
            android.R.layout.simple_list_item_2,
            arrayOf("Name", "Value"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        listCountSensor!!.adapter = mHistory


        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageURI(imageUri)
        }
    }

    fun takePhotoClick(view: View) {
        Log.d("tag", "button clicked")
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // проверка на наличие разрешений для камеры
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null && isWork) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val authorities = requireActivity().applicationContext.packageName + ".fileprovider"
            imageUri = FileProvider.getUriForFile(view.context, authorities, photoFile!!)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    fun startRecClick(view: View?) {
        try {
            startRecBut.isEnabled = false
            stopRecBut.isEnabled = true
            stopRecBut.requestFocus()
            startRecording()
        } catch (e: Exception) {
            Log.e(TAG, "Caught io exception " + e.message)
        }
    }

    fun stopRecClick(view: View?) {
        startRecBut.setEnabled(true)
        stopRecBut.setEnabled(false)
        startRecBut.requestFocus()
        stopRecording()
        processAudioFile()
    }

    fun startPlayClick(view: View?) {
        requireActivity().startService(
            Intent(context, HardwareService::class.java)
        )
        Log.d("PlayerState", "Playing start")
    }

    fun stopPlayClick(view: View?) {
        requireActivity().stopService(
            Intent(context, HardwareService::class.java)
        )
        Log.d("PlayerState", "Playing stop")
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    @Throws(IOException::class)
    private fun startRecording() {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            if (audioFile == null) {
                audioFile = File(
                    requireActivity().getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC
                    ), "rec_audio.mp3"
                )
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath())
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording")
            mediaRecorder!!.stop()
            mediaRecorder!!.reset()
            mediaRecorder!!.release()
            Toast.makeText(
                context, "You are not recording right now!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun processAudioFile() {
        Log.d(TAG, "processAudioFile")
        val values = ContentValues(4)
        val current = System.currentTimeMillis()

        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName())
        values.put(MediaStore.Audio.Media.DATE_ADDED, (current / 1000).toInt())
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath())
        val contentResolver = requireActivity().contentResolver
        Log.d(TAG, "audioFile: " + audioFile.getAbsolutePath())
        val baseUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri: Uri? = contentResolver.insert(baseUri, values)

        // оповещение системы о новом файле
        requireActivity().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.takePhotoButton -> takePhotoClick(view)
            R.id.startRecBut -> startRecClick(view)
            R.id.stopRecBut -> stopRecClick(view)
            R.id.startPlayBut -> startPlayClick(view)
            R.id.stopPlayBut -> stopPlayClick(view)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val REQUEST_CODE_PERMISSION = 100
        private const val CAMERA_REQUEST = 0
        fun newInstance(param1: String?, param2: String?): HardwareFragment {
            val fragment = HardwareFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            permission!!
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
            }
            return true
        }
    }
}