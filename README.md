# Stop-Sign-Project
2016 Summer

Setting up the coding environment: 

Required Software and Resources:

Option 1: Download these components separately

	1. Android Studio with Android SDK Manager OR Eclipse IDE
	
	2. Java SE Development Kit
	
	3. ADT (Android Developer Tools) and CDT (C/C++ Development Tool) plugins, if Eclipse is your IDE
	
	4. Android NDK

	5. OpenCV Library
	
Option 2: Download the Tegra Android Development Pack
	
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
			
			2. Install everything under "Android SDK" by selecting "install" in the dropdown box that appears under "Action"
				
				1. Accept the license agreements on the popup window that opens after you click "Next"
			
![instruction2](https://cloud.githubusercontent.com/assets/9889325/16841575/d4503f36-49a7-11e6-8a5d-daa76ef46b32.PNG)

			3. If you encounter any errors while one of the APIS (i.e. Android 3.1 (API 12)) is being installed, go to the dropdown box on the line of the API (below "Action") and select "no action". 

![instruction3](https://cloud.githubusercontent.com/assets/9889325/16842516/c1e9b81e-49ab-11e6-8bbe-2f992e61232d.png)

			4. Install all components under "Android Toolchain".
			
			5. Install "OpenCV", found under "Middleware/API"
			
			6. Install "USB Driver (NVIDIA)", found under "Developer Tools"
		
		4. Click "Finish" once everything has been installed. Restarting the computer may be necessary.
	
	4. Post-Installation Configuration
	
		1. Installing Emulator System Images
		
		2. Configuring Eclipse with NDK
		
		3. Verifying NDK (this step may lead to issues)
		
			1. Most Common Error encountered: Unsupported major.minor version 52.0

		
	
		






