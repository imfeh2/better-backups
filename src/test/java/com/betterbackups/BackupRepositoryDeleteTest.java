package com.betterbackups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BackupRepositoryDeleteTest {
	@TempDir
	Path tempDir;

	@Test
	void deletesOnlyTheRequestedBackup() throws Exception {
		Path backupDir = tempDir.resolve("backups");
		Files.createDirectories(backupDir);
		Files.writeString(backupDir.resolve("2026-05-29_10-00-00.zip"), "old");
		Files.writeString(backupDir.resolve("2026-05-29_11-00-00.zip"), "new");
		BackupRepository repository = new BackupRepository(backupDir);

		repository.deleteBackup("2026-05-29_10-00-00");

		assertEquals(1, repository.listBackups().size());
		assertTrue(Files.exists(backupDir.resolve("2026-05-29_11-00-00.zip")));
		assertFalse(Files.exists(backupDir.resolve("2026-05-29_10-00-00.zip")));
	}
}
