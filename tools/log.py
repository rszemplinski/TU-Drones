"""
  This file allows us to both log
  print statements to a file and print
  them out to the console.

  Trust me this is nice.
"""

import sys

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
    sys.stdout = PrintAndLog(sys.stdout, f)
