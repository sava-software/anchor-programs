package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record ManageRaydiumLiquidityParams(int collateralIndex,
                                           BigInteger liquidityAmount,
                                           TokenAmountsParams manageParams,
                                           byte[] assetIndexGuidance) implements Borsh {

  public static ManageRaydiumLiquidityParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var liquidityAmount = getInt128LE(_data, i);
    i += 16;
    final var manageParams = TokenAmountsParams.read(_data, i);
    i += Borsh.len(manageParams);
    final var assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new ManageRaydiumLiquidityParams(collateralIndex,
                                            liquidityAmount,
                                            manageParams,
                                            assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) collateralIndex;
    ++i;
    putInt128LE(_data, i, liquidityAmount);
    i += 16;
    i += Borsh.write(manageParams, _data, i);
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + 16 + Borsh.len(manageParams) + Borsh.lenVector(assetIndexGuidance);
  }
}
