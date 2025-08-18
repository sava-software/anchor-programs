package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record AddCollateralTimeLockArgs(CollateralTerms collateralTerms, UpdateAssetDataParams updateAssetDataParams) implements Borsh {

  public static AddCollateralTimeLockArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralTerms = CollateralTerms.read(_data, i);
    i += Borsh.len(collateralTerms);
    final var updateAssetDataParams = UpdateAssetDataParams.read(_data, i);
    return new AddCollateralTimeLockArgs(collateralTerms, updateAssetDataParams);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(collateralTerms, _data, i);
    i += Borsh.write(updateAssetDataParams, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(collateralTerms) + Borsh.len(updateAssetDataParams);
  }
}
