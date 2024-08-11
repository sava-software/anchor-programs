package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ChangeAuthorityEvent(PublicKey state,
                                   PubkeyValueChange adminChange,
                                   PubkeyValueChange validatorManagerChange,
                                   PubkeyValueChange operationalSolAccountChange,
                                   PubkeyValueChange treasuryMsolAccountChange,
                                   PubkeyValueChange pauseAuthorityChange) implements Borsh {


  public static ChangeAuthorityEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var adminChange = _data[i++] == 0 ? null : PubkeyValueChange.read(_data, i);
    if (adminChange != null) {
      i += Borsh.len(adminChange);
    }
    final var validatorManagerChange = _data[i++] == 0 ? null : PubkeyValueChange.read(_data, i);
    if (validatorManagerChange != null) {
      i += Borsh.len(validatorManagerChange);
    }
    final var operationalSolAccountChange = _data[i++] == 0 ? null : PubkeyValueChange.read(_data, i);
    if (operationalSolAccountChange != null) {
      i += Borsh.len(operationalSolAccountChange);
    }
    final var treasuryMsolAccountChange = _data[i++] == 0 ? null : PubkeyValueChange.read(_data, i);
    if (treasuryMsolAccountChange != null) {
      i += Borsh.len(treasuryMsolAccountChange);
    }
    final var pauseAuthorityChange = _data[i++] == 0 ? null : PubkeyValueChange.read(_data, i);
    return new ChangeAuthorityEvent(state,
                                    adminChange,
                                    validatorManagerChange,
                                    operationalSolAccountChange,
                                    treasuryMsolAccountChange,
                                    pauseAuthorityChange);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(adminChange, _data, i);
    i += Borsh.writeOptional(validatorManagerChange, _data, i);
    i += Borsh.writeOptional(operationalSolAccountChange, _data, i);
    i += Borsh.writeOptional(treasuryMsolAccountChange, _data, i);
    i += Borsh.writeOptional(pauseAuthorityChange, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + Borsh.lenOptional(adminChange)
         + Borsh.lenOptional(validatorManagerChange)
         + Borsh.lenOptional(operationalSolAccountChange)
         + Borsh.lenOptional(treasuryMsolAccountChange)
         + Borsh.lenOptional(pauseAuthorityChange);
  }
}
