
package org.onebeartoe.tours;

import com.github.kwart.jd.JavaDecompiler;
import com.github.kwart.jd.input.JDInput;
import com.github.kwart.jd.input.ZipFileInput;
import com.github.kwart.jd.options.DecompilerOptions;
import com.github.kwart.jd.output.DirOutput;
import com.github.kwart.jd.output.JDOutput;
import java.io.File;

public class Main 
{
    public static void main(String[] args) 
    {
        JDInput input = new ZipFileInput("/home/roberto/Versioning/owner/beto-land-owner/Websites/oneweb/onebeartoe-net/src/main/webapp/games/type-o-rama/type-o-rama.jar");
        JDOutput output = new DirOutput(new File("target/decompile"));
        JavaDecompiler decompiler = new JavaDecompiler(new DecompilerOptions() {

        @Override
        public boolean isSkipResources() {
            return true;
        }

        @Override
        public boolean isEscapeUnicodeCharacters() {
            return false;
        }

        @Override
        public boolean isDisplayLineNumbers() {
            return false;
        }

        @Override
        public boolean isParallelProcessingAllowed() {
            return true;
        }
    });
    
    input.decompile(decompiler, output);
    }
}
