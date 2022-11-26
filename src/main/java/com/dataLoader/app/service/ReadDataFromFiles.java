package com.dataLoader.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dataLoader.app.entity.User;
import com.dataLoader.app.repositories.UserRepository;

@Service
public class ReadDataFromFiles {
	@Value("${input.file.directory}")
	private String inputFileDirectory;

	@Value("${input.file.hasHeader}")
	private Boolean inputFileHasHeader;

	@Autowired
	private UserRepository repository;

	Logger logger = LoggerFactory.getLogger(ReadDataFromFiles.class);

	@Async
	public CompletableFuture<List<User>> readFileFromPath(String filePath) throws Exception {
		List<User> userList = parseCsvFile(inputFileDirectory, filePath);
		return CompletableFuture.completedFuture(userList);
	}

	public List<User> parseCsvFile(final String path, final String absoluteFile) throws Exception {
		logger.info(
				"Processing files " + path + "\\" + absoluteFile + " by thread :: " + Thread.currentThread().getName());
		List<User> users = new ArrayList<>();
		try {
			try (final BufferedReader br = new BufferedReader(new FileReader(path + "\\" + absoluteFile))) {
				String line;
				boolean hasHeader = inputFileHasHeader ? true : false;
				while ((line = br.readLine()) != null) {
					if (hasHeader) {
						hasHeader = false;
						continue;
					}
					final String[] data = line.split(",");
					final User user = new User();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					user.setName(data[0]);
					user.setDate(formatter.parse(data[2]));
					user.setAge(Integer.parseInt(data[1]));
					users.add(user);
				}
				repository.saveAll(users);
				return users;
			}

		} catch (Exception e) {
			throw new Exception("Failed to Parse CSV file{}", e);
		}

	}

	public Set<String> listFilesUsingJavaIO(String dir) {
		return Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory()).map(File::getName)
				.collect(Collectors.toSet());
	}

}
