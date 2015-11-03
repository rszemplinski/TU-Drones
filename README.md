# TU Drone Research Project

Welcome to the TU Drone Research Repo!

## You will need:
* MAVProxy
* Python 2.7 or above
* Drones

Be sure to follow the instructions on [DroneKit-Python](http://python.dronekit.io/guide/getting_started.html) to be able to set up and run our code on the drones or follow bellow.


### Getting Started Programming

 - Navigate to [3DR's DroneKit documentation](http://python.dronekit.io/)
 - Go to the [Get Started](http://python.dronekit.io/guide/getting_started.html) option
 - Follow the instructions that apply to you and your operating system
 - After you finish installing the application navigate to the Loading Dronekit Section or click this [link](http://python.dronekit.io/guide/getting_started.html#loading-dronekit)
 - Follow the instruction in the Loading Dronekit section
 - If you want to run an example for your own sake you can head to the section [Running An App/Example](http://python.dronekit.io/guide/getting_started.html#running-an-app-example)
 - Refer to the [3DR's DroneKit documentation](http://python.dronekit.io/) to learn more about the dronekit API


###Helpful Resources

 - [List Of Parameters](http://copter.ardupilot.com/wiki/arducopter-parameters/)
 - [Vehicle State and Settings](http://python.dronekit.io/guide/vehicle_state_and_parameters.html)



 ###THINGS TO REMEMBER

 **Note** if you can't get the drones to arm themselves and instead they make a sad beeping noise then you may need to recalibrate the drones.
 To recalibrate use either APM Planner or Mission Planner and navigate to the **Initial Setup** tab. Inside the tab you will see to a **Mandatory Hardware** dropdown
 which contains **Accel Calibration** and **Compass Calibration**. Use these two tabs to calibrate the drones and hopefully after you will be able to arm the drones again. :)

 **Note** that ‘set’ operations are not guaranteed to be complete until **flush()** is called on the parent **Vehicle** object.



### Troubleshooting
If drone won't start and every setting is set properly that you know of try this:
- [Vehicle Calibration Error](https://3drobotics.com/kb/troubleshooting/)