
package org.onebeartoe.tours;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * This application looks in the current working directory for file names containing 
 * a string supplied on the command line, and generates 'git mv' commands to remove the 
 * string from the filename.
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class GitRenameMover extends SimpleFileVisitor<Path>
{
    private List<Path> javaProposedPaths;
    
    private Path inpath;
    
    private String replaceTarget = "-java-";
 
    public GitRenameMover()
    {        
        final String pwd = ".";
        inpath = FileSystems.getDefault().getPath(pwd);
        
        javaProposedPaths = new ArrayList();
    }    
    
    public static void main(String [] args) throws Exception
    {        
        GitRenameMover gitMover = new GitRenameMover();
        gitMover.printRenameStrings();
    }
    
    private String newPath(Path targetPath)
    {
        File f = targetPath.toFile();
        String naem = f.getName();
        File parent = f.getParentFile();
        
        String newName = naem.replace(replaceTarget, "-");
        newName = newName.replace("BOTTTOM", "BOTTOM");
        
        String newPath = parent.getPath() + "/" + newName;
        
        // removethe leading ./
        newPath = newPath.replace(".\\", "");
        
        // replace \ with /
        newPath = newPath.replace("\\", "/");
                
        return newPath;
    }
    
    private void printOneRenameString(Path targetPath) throws Exception
    {
        String newPath = newPath(targetPath);
        
        File newFile = new File(newPath);
        if( newFile.exists() )
        {
            throw new Exception("the file already exists: " + newPath);
        }
        
        String tp = targetPath.toString().replace(".\\", "");
        tp = tp.replace("\\", "/");
        
        System.out.println("git mv " + tp + " " + newPath);
    }
    
    private void printRenameStrings() throws Exception
    {
        Files.walkFileTree(inpath, this);
        
        for(Path p : javaProposedPaths)
        {
            printOneRenameString(p);
        }
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
    {
        if (attr.isRegularFile())
        {
            final String pattern = "*-java-*.png";
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            
            Path name = file.getFileName();
            
            if (name != null && matcher.matches(name))
            {
                javaProposedPaths.add(file);
            }
        }

        return FileVisitResult.CONTINUE;
    }
}
