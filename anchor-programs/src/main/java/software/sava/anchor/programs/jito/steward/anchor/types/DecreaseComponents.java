package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DecreaseComponents(long scoringUnstakeLamports,
                                 long instantUnstakeLamports,
                                 long stakeDepositUnstakeLamports,
                                 long totalUnstakeLamports) implements Borsh {

  public static final int BYTES = 32;

  public static DecreaseComponents read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var scoringUnstakeLamports = getInt64LE(_data, i);
    i += 8;
    final var instantUnstakeLamports = getInt64LE(_data, i);
    i += 8;
    final var stakeDepositUnstakeLamports = getInt64LE(_data, i);
    i += 8;
    final var totalUnstakeLamports = getInt64LE(_data, i);
    return new DecreaseComponents(scoringUnstakeLamports,
                                  instantUnstakeLamports,
                                  stakeDepositUnstakeLamports,
                                  totalUnstakeLamports);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, scoringUnstakeLamports);
    i += 8;
    putInt64LE(_data, i, instantUnstakeLamports);
    i += 8;
    putInt64LE(_data, i, stakeDepositUnstakeLamports);
    i += 8;
    putInt64LE(_data, i, totalUnstakeLamports);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
