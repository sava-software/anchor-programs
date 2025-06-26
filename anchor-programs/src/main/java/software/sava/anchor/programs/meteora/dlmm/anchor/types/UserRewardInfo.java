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
    final var rewardPerTokenCompletes = new BigInteger[2];
    i += Borsh.readArray(rewardPerTokenCompletes, _data, i);
    final var rewardPendings = new long[2];
    Borsh.readArray(rewardPendings, _data, i);
    return new UserRewardInfo(rewardPerTokenCompletes, rewardPendings);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(rewardPerTokenCompletes, _data, i);
    i += Borsh.writeArray(rewardPendings, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
