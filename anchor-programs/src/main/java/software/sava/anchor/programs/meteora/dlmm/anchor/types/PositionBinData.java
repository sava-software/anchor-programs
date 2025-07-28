package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record PositionBinData(BigInteger liquidityShare,
                              UserRewardInfo rewardInfo,
                              FeeInfo feeInfo) implements Borsh {

  public static final int BYTES = 112;

  public static PositionBinData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var liquidityShare = getInt128LE(_data, i);
    i += 16;
    final var rewardInfo = UserRewardInfo.read(_data, i);
    i += Borsh.len(rewardInfo);
    final var feeInfo = FeeInfo.read(_data, i);
    return new PositionBinData(liquidityShare, rewardInfo, feeInfo);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, liquidityShare);
    i += 16;
    i += Borsh.write(rewardInfo, _data, i);
    i += Borsh.write(feeInfo, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
