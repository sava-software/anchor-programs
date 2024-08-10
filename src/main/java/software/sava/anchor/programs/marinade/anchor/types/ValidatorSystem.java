package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ValidatorSystem(List validatorList,
                              PublicKey managerAuthority,
                              int totalValidatorScore,
                              // sum of all active lamports staked
                              long totalActiveBalance,
                              // DEPRECATED, no longer used
                              int autoAddValidatorEnabled) implements Borsh {


  public static ValidatorSystem read(final byte[] _data, final int offset) {
    int i = offset;
    final var validatorList = List.read(_data, i);
    i += Borsh.len(validatorList);
    final var managerAuthority = readPubKey(_data, i);
    i += 32;
    final var totalValidatorScore = getInt32LE(_data, i);
    i += 4;
    final var totalActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var autoAddValidatorEnabled = _data[i] & 0xFF;
    return new ValidatorSystem(validatorList,
                               managerAuthority,
                               totalValidatorScore,
                               totalActiveBalance,
                               autoAddValidatorEnabled);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(validatorList, _data, i);
    managerAuthority.write(_data, i);
    i += 32;
    putInt32LE(_data, i, totalValidatorScore);
    i += 4;
    putInt64LE(_data, i, totalActiveBalance);
    i += 8;
    _data[i] = (byte) autoAddValidatorEnabled;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(validatorList)
         + 32
         + 4
         + 8
         + 1;
  }
}