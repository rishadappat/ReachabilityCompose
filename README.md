# ReachabilityCompose

### ReachabilityCompose makes it easier to implement network listener in Jetpack compose. You can implement the listener with a single line of code without any additional configuration or permissions.


Step 1. Add the dependency

	dependencies {
	        implementation("in.appat:ReachabilityCompose:0.1.1")
	}


Step 2. Replace

	setContent{
 
	}

 with 
	
	setNetworkListenerContent{
 
	}

Thats it.


If you want to show the message in any other view, you can use the below compose.

	ReachabilityStatus(modifier = Modifier)


If you want only the network state, use the below method.

 	val connection by reachabilityState()




https://github.com/rishadappat/ReachabilityCompose/assets/12482829/008c2c8c-08a8-400b-92c2-24f488247c07


