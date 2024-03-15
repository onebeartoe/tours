
package org.onebeartoe.tours.lambdas.streams.copy.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CopyTool
{
    private void copy() throws IOException 
    {
        Path inpath = Paths.get("src/main/resources/copy-tool/raw.data");
        
        List<String> allLines = Files.readAllLines(inpath);
        

                
        List<String> frontFormatted = allLines.stream()
                                              .map( s -> 
        {
            s = removeLeadingPath(s);            
            
            return s; 
        })
        .collect( Collectors.toList() );

        List<String> sortedLines = frontFormatted.stream()
                                        .sorted()
                                        .collect( Collectors.toList() );
        
        long sortedCount = sortedLines.size();
        System.out.println("sortedCount = " + sortedCount);

        Path sortedOutpath = Paths.get("target/copy-tool/sorted.data");
        sortedOutpath.getParent().toFile().mkdirs();
        Files.write(sortedOutpath, sortedLines);
        
        List<String> backFormatted = sortedLines.stream()
                                              .map( s -> 
        {            
            s = removeTrailingPath(s);
            
            return s; 
        })
        .collect( Collectors.toList() );
        
        List<String> unique = backFormatted.stream()
                                        .distinct()
                                        .collect( Collectors.toList() );
        
        unique.forEach(System.out::println);
        
        int size = unique.size();
        System.out.println("unique size = " + size);
        
        outputCopyCommands(unique);
    }
    
    public static void main(String [] args) throws IOException
    {
        CopyTool tool = new CopyTool();
        
        tool.copy();
    }

    private void outputCopyCommands(List<String> unique) throws IOException 
    {
        String outputPath = "~/Workspace/group/mom-songs/";
        
        List<String> commands = unique.stream()
                .map( s -> "cp -r " + '"' + s + '"' + " " + outputPath)
                .collect( Collectors.toList() );
        
        Path sortedOutpath = Paths.get("target/copy-tool/copy.sh");
        Files.write(sortedOutpath, commands);
    }

    private String removeLeadingPath(String s) 
    {
        if( s.startsWith("./") )
        {
            s = s.replace("./", "");
        }

        if( s.startsWith("../") )
        {
            s = s.replace("../", "");
        }
        
        return s;
    }

    private String removeTrailingPath(String s) 
    {
        if( s.contains("/") )
        {
            int end = s.indexOf("/");
            
            s = s.substring(0, end);
        }
        
        return s;
    }
}
