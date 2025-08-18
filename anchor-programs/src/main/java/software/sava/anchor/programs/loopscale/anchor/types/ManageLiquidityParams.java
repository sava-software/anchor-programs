package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record ManageLiquidityParams(int collateralIndex,
                                    BigInteger liquidityAmount,
                                    TransferTypeParams transferParams,
                                    byte[] assetIndexGuidance) implements Borsh {

  public static ManageLiquidityParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var liquidityAmount = getInt128LE(_data, i);
    i += 16;
    final var transferParams = TransferTypeParams.read(_data, i);
    i += Borsh.len(transferParams);
    final byte[] assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new ManageLiquidityParams(collateralIndex,
                                     liquidityAmount,
                                     transferParams,
                                     assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) collateralIndex;
    ++i;
    putInt128LE(_data, i, liquidityAmount);
    i += 16;
    i += Borsh.write(transferParams, _data, i);
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + 16 + Borsh.len(transferParams) + Borsh.lenVector(assetIndexGuidance);
  }
}
