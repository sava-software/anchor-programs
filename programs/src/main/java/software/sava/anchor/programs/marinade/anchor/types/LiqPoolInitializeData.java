package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiqPoolInitializeData(long lpLiquidityTarget,
                                    Fee lpMaxFee,
                                    Fee lpMinFee,
                                    Fee lpTreasuryCut) implements Borsh {

  public static LiqPoolInitializeData read(final byte[] _data, final int offset) {
    int i = offset;
    final var lpLiquidityTarget = getInt64LE(_data, i);
    i += 8;
    final var lpMaxFee = Fee.read(_data, i);
    i += Borsh.len(lpMaxFee);
    final var lpMinFee = Fee.read(_data, i);
    i += Borsh.len(lpMinFee);
    final var lpTreasuryCut = Fee.read(_data, i);
    return new LiqPoolInitializeData(lpLiquidityTarget,
                                     lpMaxFee,
                                     lpMinFee,
                                     lpTreasuryCut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lpLiquidityTarget);
    i += 8;
    i += Borsh.write(lpMaxFee, _data, i);
    i += Borsh.write(lpMinFee, _data, i);
    i += Borsh.write(lpTreasuryCut, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len(lpMaxFee) + Borsh.len(lpMinFee) + Borsh.len(lpTreasuryCut);
  }
}
