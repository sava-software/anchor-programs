package software.sava.anchor.programs.glam;

import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record GlamIxProxy(Discriminator discriminator,
                          Discriminator glamDiscriminator,
                          List<GlamDynamicAccountMeta> newDynamicAccounts,
                          List<GlamAccountMeta> newAccounts,
                          int[] indexes,
                          int numAccounts) {

  public static GlamIxProxy createProxy(Discriminator discriminator,
                                        Discriminator glamDiscriminator,
                                        List<GlamDynamicAccountMeta> newDynamicAccounts,
                                        List<GlamAccountMeta> newAccounts,
                                        int[] indexes) {
    final int numRemoved = (int) IntStream.of(indexes).filter(i -> i < 0).count();
    return new GlamIxProxy(
        discriminator,
        glamDiscriminator,
        newDynamicAccounts,
        newAccounts,
        indexes,
        newDynamicAccounts.size() + newAccounts.size() + (indexes.length - numRemoved)
    );
  }

  public Instruction mapInstruction(final GlamVaultAccounts glamVaultAccounts, final Instruction instruction) {
    final int discriminatorLength = discriminator.length();
    final int glamDiscriminatorLength = glamDiscriminator.length();
    final int lengthDelta = glamDiscriminatorLength - discriminatorLength;
    final int dataLength = instruction.len();
    final byte[] data;
    if (lengthDelta == 0) {
      data = new byte[dataLength];
      System.arraycopy(instruction.data(), instruction.offset(), data, 0, dataLength);
    } else {
      data = new byte[dataLength + lengthDelta];
      System.arraycopy(
          instruction.data(), instruction.offset() + discriminatorLength,
          data, discriminatorLength, dataLength - discriminatorLength
      );
    }
    glamDiscriminator.write(data, 0);

    final var mappedAccounts = new AccountMeta[numAccounts];
    for (final var glamAccountMeta : newDynamicAccounts) {
      glamAccountMeta.setAccount(mappedAccounts, glamVaultAccounts);
    }
    for (final var glamAccountMeta : newAccounts) {
      glamAccountMeta.setAccount(mappedAccounts);
    }

    final var accounts = instruction.accounts();
    final int numAccounts = accounts.size();

    int s = 0;
    int g;
    for (; s < indexes.length; ++s) {
      g = indexes[s];
      if (g >= 0) {
        mappedAccounts[g] = accounts.get(s);
      }
    }

    // Copy extra accounts.
    g = numAccounts;
    for (; s < numAccounts; ++s, ++g) {
      mappedAccounts[g] = accounts.get(s);
    }

    return Instruction.createInstruction(
        glamVaultAccounts.glamAccounts().invokedProgram(),
        Arrays.asList(mappedAccounts),
        data
    );
  }
}
