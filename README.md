# 2016-Summer-Projects
2016 Summer

##Setting up the coding environment: 

###Required Software and Resources:

####Option 1: Download these components separately

i. Android Studio with Android SDK Manager (Recommended) (http://developer.android.com/sdk/index.html) OR Eclipse IDE (http://www.eclipse.org/downloads)
	
ii. Java SE Development Kit (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
	
iii. ADT (Android Developer Tools) and CDT (C/C++ Development Tool) plugins, if Eclipse is your IDE:
		
	1. Navigate to "Help" -> "Install New Software"
	
	2. Click the "Add" button, found in the top right next to the input field
	
	3. In the "Add Respository" window, input "ADT Plug-In" in "Name" and https://dl-ssl.google.com/android/eclipse/ in "Location"
	
	4. Click "OK"
	
	5. Check the "Developer Tools" checkbox
	
	6. Click "Next"
	
	7. Ensure that the Native Support Tools plug-in is included in the list of tools shown and click "Next"
	
	8. Accept the License Agreement and click "Finish"
	
	9. After installation is completed, restart Eclipse 
	
iv. Android NDK (http://developer.android.com/tools/sdk/ndk/index.html)

v. OpenCV Library (http://sourceforge.net/projects/opencvlibrary/files/opencv-android)
	
####Option 2: Download the Tegra Android Development Pack
	
1. Register for free membership on NVIDIA website 
	
2. Download the pack: 

	1. Navigate to https://developer.nvidia.com/tegra-android-development-pack
		
	2. Click on the "Downloads" button, which navigates you to the "Download Center" page
		
	3. In the "Download Center" page, scroll until you find the "Tegra Android Development Pack" (version 4.0r2)
		
	4. Download the pack corresponding to your operating system
	
3. After you launch the installer:
	
	1. Follow the onscreen instructions after accepting the license agreement
		
	2. After selecting the directories for the NVPACK and the NVIDIA, click "Next"
		
	3. A "TADP Component Manager" window will pop up:
		
		1. Install "Documentation"
			
		2. Install everything under "Android SDK" by selecting "install" in the dropdown box that appears under "Action" (See Figure 1.0)
				
			1. Accept the license agreements on the popup window that opens after you click "Next"

		3. If you encounter any errors while one of the APIS (i.e. Android 3.1 (API 12)) is being installed, go to the dropdown box on the line of the API (below "Action") and select "no action". (See Figure 1.1)

		4. Install all components under "Android Toolchain".
			
		5. Install "OpenCV", found under "Middleware/API"
			
		6. Install "USB Driver (NVIDIA)", found under "Developer Tools"
		
	4. Click "Finish" once everything has been installed. Restarting the computer may be necessary.
	
4. Post-Installation Configuration
	
	1. Installing Emulator System Images:
		
		1. Navigate to the "NVPACK" directory that you specified on installation
			
		2. Open the "android-sdk-windows" directory
			
		3. Run the SDK Manager
			
		4. For every installed Android X.X (ex. Android 4.4.2), select the system images ("ARM EABI v7a System Image", "Intel x86 Atom_64 System Image", "Intel x86 Atom System Image"), the Google APIs, and the Google APIs for each image.
			
		5. Click "Install Packages"
			
		6. Accept the license agreements and click "Install"
		
	2. Configuring Eclipse with NDK:
		
		1. Open Eclipse from the "NVPACK" directory

		2. Navigate to "Window" (Located in the Toolbar with "File", "Edit", etc.) -> "Preferences"
			
		3. In the "Preferences" window, navigate to the "Android" tree on the left pane (Above "Ant" and Below "Android")
			
		4. Select "NDK" 
			
		5. Browse for the NDK location (should be in the "NVPACK" directory) and paste it in the "NDK Location" input field
			
		6. Click "OK"
		
		
	3. Verifying NDK (this step may lead to issues)
		
		1. Open Eclipse
			
		2. Import the "hello-jni" sample project from the "samples" folder found in the NDK directory:
			
			1. On the "Import" window that pops up, select "Existing Android Code Into Workspace" under "Android". Click "Next"
				
			2. On the proceeding window, input the location of the hello-jni project in the "Root Directory" field (i.e. "C:\NVPACK\android-ndk-r10d\samples\hello-jni")

			3. Right-click on the "hello-jni" project in the "Package Explorer" pane
				
			4. Navigate to "Android Tools" -> "Add Native Support"
				
			5. If there are errors that appear, remove the "test" folder
				
			6. Start the emulator by navigating to the Android Virtual Device Manager found on the top toolbar (It's a small icon with the android logo/symbol in a phone) 
				
				i. http://stackoverflow.com/questions/506777/how-do-i-run-an-android-emulator-automatically-from-eclipse
				
			7. Right-click on the "hello-jni" project, navigate to "Run As" -> "Android Application"
		
				1. You may encounter this error: "Unsupported major.minor version 52.0"
				
				2. Unfortunately, as of now we have not been able to resolve the above error. 
	
Figure 1.0:
![instruction2](https://cloud.githubusercontent.com/assets/9889325/16841575/d4503f36-49a7-11e6-8a5d-daa76ef46b32.PNG)

Figure 1.1:
![instruction3](https://cloud.githubusercontent.com/assets/9889325/16842516/c1e9b81e-49ab-11e6-8bbe-2f992e61232d.png)



###Creating a java project with Android Studio (If you run into any unusual runtime errors, this sample project may be outdated)

1. Open Android Studio
	
2. Create a new project and name it "HelloVisionWorld"
	
3. Set the company domain to "app0.com"
	
4. Set the Minimum SDK to API 11
	
5. Create a blank activity and name it "HelloVisionActivity"
	
6. Add OpenCV as a dependency to the project:
		
	1. Navigate to "File" -> "New" -> "Import Module"
		
	2. Navigate to the <OpenCV directory>/sdk/java (Ex. C:\NVPACK\OpenCV-2.4.8.2-Tegra-sdk\sdk\java)
		
	3. Click "Next", then "Finish"
		
	4. If you encounter any errors at this point, Android Studio will propose some quick-fixes to install missing components. Follow those instructions.
		
	5. Right-click on the "app" folder, navigate to "Open Module Settings". Click on the "Dependencies" tab, press the "+" button, select "Module Dependency", choose the OpenCV library, then click "Add". This will allow you to import OpenCV classes to your project.
		
	6. On the left project bar, navigate to "res" -> "layout" -> "content_hello_vision.xml", then add the following snippet:
	
		
			<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android" 	xmlns:tools="http://schemas.android.com/tools" xmlns:opencv="http://schemas.android.com/apk/res-auto" 	android:layout_width="match_parent" android:layout_height="match_parent" 	android:paddingBottom="@dimen/activity_vertical_margin" 	android:paddingLeft="@dimen/activity_horizontal_margin" 		android:paddingRight="@dimen/activity_horizontal_margin" android:paddingTop="@dimen/activity_vertical_margin" tools:context="com.example.hellovisionworld.HelloVisionActivity"> 
			
			<org.opencv.android.JavaCameraView android:layout_width="fill_parent" android:layout_height="fill_parent" android:visibility="gone" android:id="@+id/HelloVisionView" opencv:show_fps="true" opencv:camera_id="any"	/> 
		
			</RelativeLayout>
	
	7. Add the following permissions to the AndroidManifest.xml file, located in the "manifests" folder under "app":
	
			These permissions should be placed below the "</application>" tag
			<uses-permission android:name="android.permission.CAMERA"/>

    			<uses-feature android:name="android.hardware.camera"/>
    			<uses-feature android:name="android.hardware.camera.autofocus"/>
    			<uses-feature android:name="android.hardware.camera.front"/>
    			<uses-feature android:name="android.hardware.camera.front.autofocus"/>
    		
    8. Modify the AndroidManifest.xml file with the following snippet:
    	
    			<application
        			android:allowBackup="true"
        			android:icon="@mipmap/ic_launcher"
        			android:label="@string/app_name"
        			android:supportsRtl="true"
        			android:theme="@android:style/Theme.NoTitleBar.Fullscreen">

	(Note: Before you complete the following step, create an emulator using the Android Virtual Device Manager)
	
	ix. Open the command prompt and use the following command:
	
		C:\NVPACK>adb install OpenCV-2.4.8.2-Tegra-sdk\apk\OpenCV_2.4.8.2_Manager_2.17_<Package that corresponds to the hardware platform>.apk
		
	Refer to the following table:
	
	![table](https://cloud.githubusercontent.com/assets/9889325/16877438/0f575c8a-4a76-11e6-8390-08bb6b388270.PNG)
	
	For example, for a 7" WSYGA tablet with an API Level of 19, the ABI or hardware platform is armeabi-v7a and the corresponding package name would be armv7a-neon.
	
	x. Add the following to HelloVisionActivity (found under the "java"->"com.app0.hellovisionworld"):
	
		private static final String TAG = "HelloVisionWorld";
    		private CameraBridgeViewBase mOpenCvCameraView;
    		private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this){
        		@Override
        		public void onManagerConnected(int status){
            			switch(status){
                			case LoaderCallbackInterface.SUCCESS:{
                    				Log.i(TAG, "OpenCV loaded successfully");
                    				mOpenCvCameraView.enableView();
                    				break;
                			}
                			default:{
                    			super.onManagerConnected(status);
                    			break;
                			}
            			}
        		}
    		};
    	
    xi. Add and Update the onResume() method:
    	
    		public void onResume(){
        		super.onResume();
        		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    		}
    		
    xii. Navigate to the "openCVLibrary2482" -> "java" -> "org.opencv" -> "android" -> "AsyncServiceHelper" and modify the file
    		
    		Replace:
    		AsyncServiceHelper helper = new AsyncServiceHelper(Version, AppContext, Callback);
        	if (AppContext.bindService(new Intent("org.opencv.engine.BIND"),
                	helper.mServiceConnection, Context.BIND_AUTO_CREATE))
        	{
            		return true;
        	}
        	With:
    		AsyncServiceHelper helper = new AsyncServiceHelper(Version, AppContext, Callback);
        	Intent intent = new Intent("org.opencv.engine.BIND");
        	intent.setPackage("org.opencv.engine");
        	if (AppContext.bindService(intent,
                	helper.mServiceConnection, Context.BIND_AUTO_CREATE))
        	{
            		return true;
        	}
        	
    xiii. Change the class header to:
    		
    		public class HelloVisionActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2
    		
    xiv. Fix import errors (alt+enter) and add the following methods:
    
    		public void onCameraViewStopped(){}
    		public void onCameraViewStarted(int x, int y){}
    		
    xv. Modify the onCreate method:
    
    		@Override
		    protected void onCreate(Bundle savedInstanceState) {
		        Log.i(TAG, "called onCreate");
		        super.onCreate(savedInstanceState);
		        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		        setContentView(R.layout.activity_hello_vision);
		        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloVisionView);
		        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		        mOpenCvCameraView.setCvCameraViewListener(this);
		        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		        setSupportActionBar(toolbar);
		
		        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		        fab.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View view) {
		                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
		                        .setAction("Action", null).show();
		            }
		        });
		    }
   
   xvi. Add the following method to the activity (import the Mat class after adding the snippet): 
   
   		public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
		        return inputFrame.rgba();
		    }
		    
  xvii. Now you are ready to run the app on an emulator!
  
  
###Important Notes:

The following code snippet should be used in all projects that implement OpenCV algorithms:
    
        	
	private static final String TAG = "HelloVisionWorld";
    		private CameraBridgeViewBase mOpenCvCameraView;
    		private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this){
        		@Override
        		public void onManagerConnected(int status){
            			switch(status){
                			case LoaderCallbackInterface.SUCCESS:{
                    				Log.i(TAG, "OpenCV loaded successfully");
                    				mOpenCvCameraView.enableView();
                    				break;
                			}
                			default:{
                    			super.onManagerConnected(status);
                    			break;
                			}
            			}
        		}
    		};	
    	
    	@Override //overrides the onResume() function found in the parent class AppCompatActivity	
    	public void onResume(){
        		super.onResume();
        		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    		}


This code is crucial because it reduces the memory footprint of an activity by loading the OpenCV library asynchronously using the OpenCV manager service. 

If the change indicated in step xii is not made, the following runtime error will result:
	
	java.lang.IllegalArgumentException: Service Intent must be explicit
	







