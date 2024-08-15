package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record InitializeEvent(PublicKey state,
                              InitializeData params,
                              PublicKey stakeList,
                              PublicKey validatorList,
                              PublicKey msolMint,
                              PublicKey operationalSolAccount,
                              PublicKey lpMint,
                              PublicKey lpMsolLeg,
                              PublicKey treasuryMsolAccount) implements Borsh {

  public static final int BYTES = 448;

  public static InitializeEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var params = InitializeData.read(_data, i);
    i += Borsh.len(params);
    final var stakeList = readPubKey(_data, i);
    i += 32;
    final var validatorList = readPubKey(_data, i);
    i += 32;
    final var msolMint = readPubKey(_data, i);
    i += 32;
    final var operationalSolAccount = readPubKey(_data, i);
    i += 32;
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var lpMsolLeg = readPubKey(_data, i);
    i += 32;
    final var treasuryMsolAccount = readPubKey(_data, i);
    return new InitializeEvent(state,
                               params,
                               stakeList,
                               validatorList,
                               msolMint,
                               operationalSolAccount,
                               lpMint,
                               lpMsolLeg,
                               treasuryMsolAccount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    i += Borsh.write(params, _data, i);
    stakeList.write(_data, i);
    i += 32;
    validatorList.write(_data, i);
    i += 32;
    msolMint.write(_data, i);
    i += 32;
    operationalSolAccount.write(_data, i);
    i += 32;
    lpMint.write(_data, i);
    i += 32;
    lpMsolLeg.write(_data, i);
    i += 32;
    treasuryMsolAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
