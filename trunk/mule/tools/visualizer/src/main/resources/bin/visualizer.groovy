import org.mule.util.SystemUtils
import org.mule.tools.visualizer.MuleVisualizer

assert args.size() : "No cmd parameters passed in"

def output = 'visualizer-output'

def nativeLibPath = '../lib/native/visualizer'
def exec = SystemUtils.IS_OS_WINDOWS ? "$nativeLibPath/dot.exe" : "$nativeLibPath/dot"

// for unix flavor check local path and fallback to a globally installed package
if (SystemUtils.IS_OS_UNIX) {
    if (!new File(exec).exists()) {
        // check global path
        exec = 'which dot'.execute().getText()
    }
}

// add more cmd parameters
def newArgs = args.toList()
newArgs << '-exec' << exec

newArgs << '-outputdir'
newArgs << output 

MuleVisualizer.main(newArgs as String[])

