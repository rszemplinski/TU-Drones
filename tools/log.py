"""
  This file allows us to both log
  print statements to a file and print
  them out to the console.

  Trust me this is nice.
"""

import sys
from datetime import datetime

backup = sys.stdout
f = open("log.log", "a")

class PrintAndLog(object):
    def __init__(self, *files):
        self.files = files
    def write(self, obj):
        for f in self.files:
            f.write(obj)

def stop_log():
    sys.stdout = backup
    f.close()

def start_log():
    f.write("\n===============================================\n\n")
    f.write(str(datetime.now()) + "\n\n")
    sys.stdout = PrintAndLog(sys.stdout, f)
