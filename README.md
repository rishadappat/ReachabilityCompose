# ReachabilityCompose

[![](https://jitpack.io/v/rishadappat/ReachabilityCompose.svg)](https://jitpack.io/#rishadappat/ReachabilityCompose)


Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.rishadappat:ReachabilityCompose:0.1.0'
	}


Step 3. Replace

	setContent{
 
	}

 with 
	
	setNetworkListenerContent{
 
	}

Thats it.


If you want to show the message in any other place, you can use the below compose.

	ReachabilityStatus(modifier = Modifier)


If you want only the network state, use the below code.

 	val connection by reachabilityState()




https://github.com/rishadappat/ReachabilityCompose/assets/12482829/008c2c8c-08a8-400b-92c2-24f488247c07


