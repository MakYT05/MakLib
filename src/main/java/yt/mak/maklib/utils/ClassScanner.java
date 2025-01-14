package yt.mak.maklib.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {

    public static List<Class<?>> findClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();

        try {
            URL packageUrl = ClassLoader.getSystemClassLoader().getResource(packageName.replace(".", "/"));

            if (packageUrl != null) {
                File packageDirectory = new File(packageUrl.toURI());

                if (packageDirectory.exists() && packageDirectory.isDirectory()) {
                    findClassesInDirectory(packageDirectory, packageName, annotation, classes);
                } else {
                    System.err.println("Package path is not a directory: " + packageName);
                }
            } else {
                System.err.println("Package URL is null for package: " + packageName);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void findClassesInDirectory(File directory, String packageName, Class<? extends Annotation> annotation, List<Class<?>> classes) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findClassesInDirectory(file, packageName + "." + file.getName(), annotation, classes);
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");

                    try {
                        Class<?> clazz = Class.forName(className);

                        if (clazz.isAnnotationPresent(annotation)) {
                            classes.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println("Class not found: " + className);
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.err.println("Unable to list files in directory: " + directory.getAbsolutePath());
        }
    }
}