Some sandbox detection techniques
    
    * Delaying execution by some time
        Due to much malware analysys queque, sandbox typicaly spend approx. 5-10minutes
        to analyze malware. Delaying execution by some time can avoid detection.
    * Looking for action's not existing in sandbox
        Such as system reboot, user interaction. If they won't exist, you know you are in sandbox.
     
Looking for files   
    SbieDLL.dll exist only in sandbox. If present = sandbox. Detected by VMRays