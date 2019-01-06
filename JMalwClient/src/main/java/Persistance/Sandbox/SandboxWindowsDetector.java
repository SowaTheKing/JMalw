package Persistance.Sandbox;

import java.io.File;

public class SandboxWindowsDetector {
    public static final String PATH_DLL = "C:\\Program Files\\sandboxie\\";

    public static final String SBIEDLL_DLL_FILENAME = "sbiedll.dll";

    public static final String SYSTEM32_DRIVERS_VBOXMOUSE_SYS =      "system32\\drivers\\VBoxMouse.sys";
    public static final String SYSTEM32_DRIVERS_VBOXGUEST_SYS =      "system32\\drivers\\VBoxGuest.sys";
    public static final String SYSTEM32_DRIVERS_VBOXSF_SYS =         "system32\\drivers\\VBoxSF.sys";
    public static final String SYSTEM32_DRIVERS_VBOXVIDEO_SYS =      "system32\\drivers\\VBoxVideo.sys";
    public static final String SYSTEM32_DRIVERS_VMMOUSE_SYS         = "system32\\drivers\\vmmouse.sys";
    public static final String SYSTEM32_DRIVERS_VMHGFS_SYS          = "system32\\drivers\\vmhgfs.sys";
    public static final String SYSTEM32_DRIVERS_VM3DMP_SYS          = "system32\\drivers\\vm3dmp.sys";
    public static final String SYSTEM32_DRIVERS_VMCI_SYS            = "system32\\drivers\\vmci.sys";
    public static final String SYSTEM32_DRIVERS_VMMEMCTL_SYS        = "system32\\drivers\\vmmemctl.sys";
    public static final String SYSTEM32_DRIVERS_VMRAWDSK_SYS        = "system32\\drivers\\vmrawdsk.sys";
    public static final String SYSTEM32_DRIVERS_VMUSBMOUSE_SYS      = "system32\\drivers\\vmusbmouse.sys";

    public static final String SYSTEM32_VBOXDISP_DLL                = "system32\\vboxdisp.dll";
    public static final String SYSTEM32_VBOXHOOK_DLL                = "system32\\vboxhook.dll";
    public static final String SYSTEM32_VBOXMRXNP_DLL               = "system32\\vboxmrxnp.dll";
    public static final String SYSTEM32_VBOXOGL_DLL                 = "system32\\vboxogl.dll";
    public static final String SYSTEM32_VBOXOGLARRAYSPU_DLL         = "system32\\vboxoglarrayspu.dll";
    public static final String SYSTEM32_VBOXOGLCRUTIL_DLL           = "system32\\vboxoglcrutil.dll";
    public static final String SYSTEM32_VBOXOGLERRORSPU_DLL         = "system32\\vboxoglerrorspu.dll";
    public static final String SYSTEM32_VBOXOGLFEEDBACKSPU_DLL      = "system32\\vboxoglfeedbackspu.dll";
    public static final String SYSTEM32_VBOXOGLPACKSPU_DLL          = "system32\\vboxoglpackspu.dll";
    public static final String SYSTEM32_VBOXOGLPASSTHROUGHSPU_DLL   = "system32\\vboxoglpassthroughspu.dll";

    public static final String SYSTEM32_VBOXSERVICE_EXE             = "system32\\vboxservice.exe";
    public static final String SYSTEM32_VBOXTRAY_EXE                = "system32\\vboxtray.exe";
    public static final String SYSTEM32_VBOXCONTROL_EXE             = "system32\\VBoxControl.exe";



    public boolean isSbiedll_dllPresent() {
        return new File(PATH_DLL + SBIEDLL_DLL_FILENAME).isFile();
    }
}
