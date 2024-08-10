package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ChangeAuthorityData(PublicKey admin,
                                  PublicKey validatorManager,
                                  PublicKey operationalSolAccount,
                                  PublicKey treasuryMsolAccount,
                                  PublicKey pauseAuthority) implements Borsh {


  public static ChangeAuthorityData read(final byte[] _data, final int offset) {
    int i = offset;
    final var admin = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (admin != null) {
      i += 32;
    }
    final var validatorManager = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (validatorManager != null) {
      i += 32;
    }
    final var operationalSolAccount = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (operationalSolAccount != null) {
      i += 32;
    }
    final var treasuryMsolAccount = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (treasuryMsolAccount != null) {
      i += 32;
    }
    final var pauseAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new ChangeAuthorityData(admin,
                                   validatorManager,
                                   operationalSolAccount,
                                   treasuryMsolAccount,
                                   pauseAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(admin, _data, i);
    i += Borsh.writeOptional(validatorManager, _data, i);
    i += Borsh.writeOptional(operationalSolAccount, _data, i);
    i += Borsh.writeOptional(treasuryMsolAccount, _data, i);
    i += Borsh.writeOptional(pauseAuthority, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(admin, 32)
         + Borsh.lenOptional(validatorManager, 32)
         + Borsh.lenOptional(operationalSolAccount, 32)
         + Borsh.lenOptional(treasuryMsolAccount, 32)
         + Borsh.lenOptional(pauseAuthority, 32);
  }
}