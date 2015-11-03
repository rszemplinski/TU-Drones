from dronekit import connect
import ipdb
import time

print("Trying to find vehicle...")
vehicle = connect("/dev/ttyACM0", await_params=True)
vehicle.close()
