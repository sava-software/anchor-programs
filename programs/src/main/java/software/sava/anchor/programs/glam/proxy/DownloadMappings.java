package software.sava.anchor.programs.glam.proxy;

import software.sava.anchor.programs.glam.GlamVaultAccounts;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static java.nio.file.StandardOpenOption.*;

public final class DownloadMappings {

  public static void main(final String[] args) throws InterruptedException {
    final var programs = args.length == 0
        ? Set.of("drift", "jupiter_gov", "jupiter_vote", "marinade", "meteora_dlmm")
        : Set.of(args);

    final var packagePath = GlamVaultAccounts.class.getPackage().getName().replace('.', '/');
    final var resourcePath = Path.of("programs/src/main/resources/" + packagePath + '/');
    final var baseURL = "https://raw.githubusercontent.com/glamsystems/glam/refs/heads/main/anchor/programs/glam/src/cpi_autogen/remapping/";

    final var semaphore = new Semaphore(4);
    final var latch = new CountDownLatch(programs.size());

    try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try (final var httpClient = HttpClient.newBuilder().executor(executor).build()) {
        for (final var program : programs) {
          executor.execute(() -> {
            try {
              final var fileName = program + ".json";
              final var uri = URI.create(baseURL + fileName);
              final var httpRequest = HttpRequest.newBuilder(uri).build();
              final var resourceFilePath = resourcePath.resolve(fileName);
              String body;
              for (long errorCount = 0; ; ) {
                semaphore.acquire();
                try {
                  System.out.println("Downloading " + fileName);
                  final var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                  body = response.body();
                  break;
                } catch (final IOException ioException) {
                  System.err.println("Failed to download " + fileName + ": " + ioException.getMessage());
                  Thread.sleep(Math.min(13_000, ++errorCount * 1_000));
                } finally {
                  semaphore.release();
                }
              }
              Files.writeString(resourceFilePath, body, CREATE, WRITE, TRUNCATE_EXISTING);
            } catch (final IOException e) {
              throw new UncheckedIOException(e);
            } catch (final InterruptedException e) {
              Thread.interrupted();
            } finally {
              latch.countDown();
            }
          });
          Thread.sleep(200);
        }

        latch.await();
      }
    }
  }
}
