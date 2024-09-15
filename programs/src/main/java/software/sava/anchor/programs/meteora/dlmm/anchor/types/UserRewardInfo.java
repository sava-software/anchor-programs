package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

public record UserRewardInfo(BigInteger[] rewardPerTokenCompletes, long[] rewardPendings) implements Borsh {

  public static final int BYTES = 48;

  public static UserRewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardPerTokenCompletes = Borsh.readArray(new BigInteger[2], _data, i);
    i += Borsh.fixedLen(rewardPerTokenCompletes);
    final var rewardPendings = Borsh.readArray(new long[2], _data, i);
    return new UserRewardInfo(rewardPerTokenCompletes, rewardPendings);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.fixedWrite(rewardPerTokenCompletes, _data, i);
    i += Borsh.fixedWrite(rewardPendings, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
