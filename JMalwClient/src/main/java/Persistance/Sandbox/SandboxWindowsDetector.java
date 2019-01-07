package Persistance.Sandbox;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


/*
            Prawdopodobnie pownienem tutaj uzyc statycznego ENUMA
 */
public class SandboxWindowsDetector {
    public static final String PATH_DLL = "C:\\Program Files\\sandboxie\\";

    public static final String SBIEDLL_DLL_FILENAME = "sbiedll.dll";

    public static final String SYSTEM32_DRIVERS_VBOXMOUSE_SYS = "system32\\drivers\\VBoxMouse.sys";
    public static final String SYSTEM32_DRIVERS_VBOXGUEST_SYS = "system32\\drivers\\VBoxGuest.sys";
    public static final String SYSTEM32_DRIVERS_VBOXSF_SYS = "system32\\drivers\\VBoxSF.sys";
    public static final String SYSTEM32_DRIVERS_VBOXVIDEO_SYS = "system32\\drivers\\VBoxVideo.sys";
    public static final String SYSTEM32_DRIVERS_VMMOUSE_SYS = "system32\\drivers\\vmmouse.sys";
    public static final String SYSTEM32_DRIVERS_VMHGFS_SYS = "system32\\drivers\\vmhgfs.sys";
    public static final String SYSTEM32_DRIVERS_VM3DMP_SYS = "system32\\drivers\\vm3dmp.sys";
    public static final String SYSTEM32_DRIVERS_VMCI_SYS = "system32\\drivers\\vmci.sys";
    public static final String SYSTEM32_DRIVERS_VMMEMCTL_SYS = "system32\\drivers\\vmmemctl.sys";
    public static final String SYSTEM32_DRIVERS_VMRAWDSK_SYS = "system32\\drivers\\vmrawdsk.sys";
    public static final String SYSTEM32_DRIVERS_VMUSBMOUSE_SYS = "system32\\drivers\\vmusbmouse.sys";

    public static final String SYSTEM32_VBOXDISP_DLL = "system32\\vboxdisp.dll";
    public static final String SYSTEM32_VBOXHOOK_DLL = "system32\\vboxhook.dll";
    public static final String SYSTEM32_VBOXMRXNP_DLL = "system32\\vboxmrxnp.dll";
    public static final String SYSTEM32_VBOXOGL_DLL = "system32\\vboxogl.dll";
    public static final String SYSTEM32_VBOXOGLARRAYSPU_DLL = "system32\\vboxoglarrayspu.dll";
    public static final String SYSTEM32_VBOXOGLCRUTIL_DLL = "system32\\vboxoglcrutil.dll";
    public static final String SYSTEM32_VBOXOGLERRORSPU_DLL = "system32\\vboxoglerrorspu.dll";
    public static final String SYSTEM32_VBOXOGLFEEDBACKSPU_DLL = "system32\\vboxoglfeedbackspu.dll";
    public static final String SYSTEM32_VBOXOGLPACKSPU_DLL = "system32\\vboxoglpackspu.dll";
    public static final String SYSTEM32_VBOXOGLPASSTHROUGHSPU_DLL = "system32\\vboxoglpassthroughspu.dll";

    public static final String SYSTEM32_VBOXSERVICE_EXE = "system32\\vboxservice.exe";
    public static final String SYSTEM32_VBOXTRAY_EXE = "system32\\vboxtray.exe";
    public static final String SYSTEM32_VBOXCONTROL_EXE = "system32\\VBoxControl.exe";

    private static final String PROCESS_VBOXSERVICE_EXE = "vboxservice.exe";
    private static final String PROCESS_VBOXTRAY_EXE = "vboxtray.exe";
    private static final String PROCESS_VMTOOLSD_EXE = "vmtoolsd.exe";
    private static final String PROCESS_VMARETRAY_EXE = "vmwaretray.exe";
    private static final String PROCESS_VMWAREUSER_EXE = "vmwareuser.exe";
    private static final String PROCESS_VGAUTHSERVICE_EXE = "VGAuthService.exe";
    private static final String PROCESS_VMACTHLP_EXE = "vmacthlp.exe";
    private static final String PROCESS_VMSRVS_EXE = "vmsrvc.exe";
    private static final String PROCESS_VMUSRVC_EXE = "vmusrvc.exe";
    private static final String PROCESS_PRLCC_EXE = "prl_cc.exe";
    private static final String PROCESS_PRLTOOLS_EXE = "prl_tools.exe";
    private static final String PROCESS_XENSERVICE_EXE = "xenservice.exe";
    private static final String PROCESS_QEMUGA_EXE = "qemu-ga.exe";


    public int scoreSandbox() {
        int score = 0;


        List<String> processList = getProcessList();
        for (String process : processList) {
            if (isSandboxProcess(process)) {
                score += 10;
            }
        }
        return score;


    }

    // I think this is not null-safe, we should compare PROCESS_VBOX... to Process
    private boolean isSandboxProcess(String process) {
        return process.contains(PROCESS_VBOXSERVICE_EXE)
                || process.contains(PROCESS_VBOXTRAY_EXE)
                || process.contains(PROCESS_VMTOOLSD_EXE)
                || process.contains(PROCESS_VMARETRAY_EXE)
                || process.contains(PROCESS_VMWAREUSER_EXE)
                || process.contains(PROCESS_VGAUTHSERVICE_EXE)
                || process.contains(PROCESS_VMACTHLP_EXE)
                || process.contains(PROCESS_VMSRVS_EXE)
                || process.contains(PROCESS_VMUSRVC_EXE)
                || process.contains(PROCESS_PRLCC_EXE)
                || process.contains(PROCESS_PRLTOOLS_EXE)
                || process.contains(PROCESS_QEMUGA_EXE)
                || process.contains(PROCESS_XENSERVICE_EXE);
    }

    private List<String> getProcessList() {
        return ProcessHandle.allProcesses()
                .map(processHandle -> processHandle.info().command())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public enum FileScanMethod {
        CREATE_NEW_FILE,
        TRY_TO_OVERWEITE,
        SYMLINK
    }

    public boolean isSandboxFilesPresent(FileScanMethod method) {
        if (FileScanMethod.CREATE_NEW_FILE.equals(method)) {
            return sandboxFileScanCreateNewFile();
        } else if (FileScanMethod.TRY_TO_OVERWEITE.equals(method)) {
            /*return sandboxFileScanTryToOverwrite();*/
        } else if (FileScanMethod.SYMLINK.equals(method)) {
            /*return andboxFileScanSymlink();*/
        }
        /*throw UnexpectedFileScanMethodProvided();*/
        return true;
    }

    private boolean sandboxFileScanCreateNewFile() {
        // Check drivers
        // Determine where system32 is present
        List<String> drives = getDiskDrives();
        for (String drive : drives) {
            if (new File(drive + SYSTEM32_DRIVERS_VBOXMOUSE_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VBOXGUEST_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VBOXSF_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VBOXVIDEO_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMMOUSE_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMHGFS_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VM3DMP_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMCI_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMMEMCTL_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMRAWDSK_SYS).exists())
                return true;
            if (new File(drive + SYSTEM32_DRIVERS_VMUSBMOUSE_SYS).exists())
                return true;
        }
        return false;
    }

    private List<String> getDiskDrives() {
        List<String> drives = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File file : roots) {
            drives.add(file.getPath());
        }

        return drives.isEmpty() ? Collections.emptyList() : drives;

    }
}

