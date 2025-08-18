package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StakedSettingsConfig(PublicKey oracle,
                                   WrappedI80F48 assetWeightInit,
                                   WrappedI80F48 assetWeightMaint,
                                   long depositLimit,
                                   long totalAssetValueInitLimit,
                                   int oracleMaxAge,
                                   // WARN: You almost certainly want "Collateral", using Isolated risk tier makes the asset
                                   // worthless as collateral, and is generally useful only when creating a staked collateral pool
                                   // for rewards purposes only.
                                   RiskTier riskTier) implements Borsh {

  public static final int BYTES = 83;

  public static StakedSettingsConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var assetWeightInit = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightInit);
    final var assetWeightMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightMaint);
    final var depositLimit = getInt64LE(_data, i);
    i += 8;
    final var totalAssetValueInitLimit = getInt64LE(_data, i);
    i += 8;
    final var oracleMaxAge = getInt16LE(_data, i);
    i += 2;
    final var riskTier = RiskTier.read(_data, i);
    return new StakedSettingsConfig(oracle,
                                    assetWeightInit,
                                    assetWeightMaint,
                                    depositLimit,
                                    totalAssetValueInitLimit,
                                    oracleMaxAge,
                                    riskTier);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oracle.write(_data, i);
    i += 32;
    i += Borsh.write(assetWeightInit, _data, i);
    i += Borsh.write(assetWeightMaint, _data, i);
    putInt64LE(_data, i, depositLimit);
    i += 8;
    putInt64LE(_data, i, totalAssetValueInitLimit);
    i += 8;
    putInt16LE(_data, i, oracleMaxAge);
    i += 2;
    i += Borsh.write(riskTier, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
