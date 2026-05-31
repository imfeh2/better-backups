package com.betterbackups;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class BackupCommandSourceTest {
	@Test
	void cleanupCommandReplacesClearCommand() throws Exception {
		String source = Files.readString(Path.of("src/main/java/com/betterbackups/BackupCommand.java"));

		assertTrue(source.contains("literal(\"cleanup\")"));
		assertTrue(source.contains("literal(\"delete\")"));
		assertTrue(source.contains("/backup cleanup"));
		assertTrue(source.contains("/backup delete"));
		assertTrue(source.contains("literal(\"latest\")"));
		assertTrue(source.contains("/backup restore latest"));
		assertTrue(source.contains("literal(\"cleanup-confirm\")"));
		assertTrue(source.contains("/backup set cleanup-confirm"));
		assertFalse(source.contains("literal(\"clear\")"));
		assertFalse(source.contains("/backup clear"));
		assertFalse(source.contains("literal(\"clear-confirm\")"));
		assertFalse(source.contains("/backup set clear-confirm"));
	}

	@Test
	void deleteCommandDoesNotUseCleanupProtection() throws Exception {
		String source = Files.readString(Path.of("src/main/java/com/betterbackups/BackupCommand.java"));

		assertTrue(source.contains("manager.requireCanDeleteBackup()"));
		assertFalse(source.contains("manager.requireCanClearBackups();\n\t\t\tString backupName = StringArgumentType.getString(context, \"backup\");"));
	}
}
