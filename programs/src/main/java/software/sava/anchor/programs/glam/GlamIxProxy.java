package software.sava.anchor.programs.glam;

import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import java.util.Arrays;

public record GlamIxProxy(Discriminator discriminator,
                          Discriminator glamDiscriminator,
                          GlamAccountMeta[] glamAccountMetaArray,
                          int[] indexes,
                          int numAccounts) {

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
      System.arraycopy(instruction.data(), instruction.offset() + discriminatorLength, data, discriminatorLength, dataLength - discriminatorLength);
    }
    glamDiscriminator.write(data, 0);

    final var mappedAccounts = new AccountMeta[numAccounts];
    for (final var glamAccountMeta : glamAccountMetaArray) {
      glamAccountMeta.setAccount(mappedAccounts, glamVaultAccounts);
    }

    final var accounts = instruction.accounts();
    final int numAccounts = accounts.size();

    if (this.numAccounts != numAccounts) {
      throw new IllegalStateException(String.format(
          """
              Expected %d accounts for %s instruction %s, not %d
              """
          ,
          this.numAccounts,
          instruction.programId(),
          discriminator,
          numAccounts
      ));
    }

    for (int s = 0, g; s < numAccounts; ++s) {
      g = indexes[s];
      mappedAccounts[g] = accounts.get(s);
    }

    return Instruction.createInstruction(
        glamVaultAccounts.glamAccounts().invokedProgram(),
        Arrays.asList(mappedAccounts),
        data
    );
  }
}
