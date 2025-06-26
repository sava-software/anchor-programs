package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

// Vault Mode. 0 = Prorata, 1 = FirstComeFirstServe. Putting 0 as Prorata for backward compatibility.
public enum VaultMode implements Borsh.Enum {

  Prorata,
  Fcfs;

  public static VaultMode read(final byte[] _data, final int offset) {
    return Borsh.read(VaultMode.values(), _data, offset);
  }
}