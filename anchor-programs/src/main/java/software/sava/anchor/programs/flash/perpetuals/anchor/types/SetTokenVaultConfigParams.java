package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetTokenVaultConfigParams(TokenPermissions tokenPermissions,
                                        long withdrawTimeLimit,
                                        long withdrawInstantFee,
                                        long[] stakeLevel) implements Borsh {

  public static final int BYTES = 67;
  public static final int STAKE_LEVEL_LEN = 6;

  public static SetTokenVaultConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenPermissions = TokenPermissions.read(_data, i);
    i += Borsh.len(tokenPermissions);
    final var withdrawTimeLimit = getInt64LE(_data, i);
    i += 8;
    final var withdrawInstantFee = getInt64LE(_data, i);
    i += 8;
    final var stakeLevel = new long[6];
    Borsh.readArray(stakeLevel, _data, i);
    return new SetTokenVaultConfigParams(tokenPermissions,
                                         withdrawTimeLimit,
                                         withdrawInstantFee,
                                         stakeLevel);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(tokenPermissions, _data, i);
    putInt64LE(_data, i, withdrawTimeLimit);
    i += 8;
    putInt64LE(_data, i, withdrawInstantFee);
    i += 8;
    i += Borsh.writeArrayChecked(stakeLevel, 6, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
