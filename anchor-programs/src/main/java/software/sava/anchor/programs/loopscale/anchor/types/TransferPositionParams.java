package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record TransferPositionParams(BigInteger liquidityAmount,
                                     int collateralIndex,
                                     TransferTypeParams transferParams,
                                     int tickLowerIndex,
                                     int tickUpperIndex,
                                     byte[] assetIndexGuidance) implements Borsh {

  public static TransferPositionParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var liquidityAmount = getInt128LE(_data, i);
    i += 16;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var transferParams = TransferTypeParams.read(_data, i);
    i += Borsh.len(transferParams);
    final var tickLowerIndex = getInt32LE(_data, i);
    i += 4;
    final var tickUpperIndex = getInt32LE(_data, i);
    i += 4;
    final byte[] assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new TransferPositionParams(liquidityAmount,
                                      collateralIndex,
                                      transferParams,
                                      tickLowerIndex,
                                      tickUpperIndex,
                                      assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, liquidityAmount);
    i += 16;
    _data[i] = (byte) collateralIndex;
    ++i;
    i += Borsh.write(transferParams, _data, i);
    putInt32LE(_data, i, tickLowerIndex);
    i += 4;
    putInt32LE(_data, i, tickUpperIndex);
    i += 4;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 16
         + 1
         + Borsh.len(transferParams)
         + 4
         + 4
         + Borsh.lenVector(assetIndexGuidance);
  }
}
