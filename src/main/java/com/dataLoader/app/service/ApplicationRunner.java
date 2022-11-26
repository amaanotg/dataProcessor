package com.dataLoader.app.service;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

	@Value("${input.file.directory}")
	private String inputFileDirectory;

	@Autowired
	private ReadDataFromFiles readDataFromFiles;

	Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("Processing files to read data");

		Set<String> fileList = listFilesUsingJavaIO(inputFileDirectory);
		for (String file : fileList) {
			readDataFromFiles.readFileFromPath(file);
		}

		logger.info("Processing files to read data has completed");

	}

	public Set<String> listFilesUsingJavaIO(String dir) {
		return Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory()).map(File::getName)
				.collect(Collectors.toSet());
	}
}
