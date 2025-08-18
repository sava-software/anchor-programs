package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record StakedSettingsEditConfig(PublicKey oracle,
                                       WrappedI80F48 assetWeightInit,
                                       WrappedI80F48 assetWeightMaint,
                                       OptionalLong depositLimit,
                                       OptionalLong totalAssetValueInitLimit,
                                       OptionalInt oracleMaxAge,
                                       // WARN: You almost certainly want "Collateral", using Isolated risk tier makes the asset
                                       // worthless as collateral, making all outstanding accounts eligible to be liquidated, and is
                                       // generally useful only when creating a staked collateral pool for rewards purposes only.
                                       RiskTier riskTier) implements Borsh {

  public static StakedSettingsEditConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oracle = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (oracle != null) {
      i += 32;
    }
    final var assetWeightInit = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (assetWeightInit != null) {
      i += Borsh.len(assetWeightInit);
    }
    final var assetWeightMaint = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (assetWeightMaint != null) {
      i += Borsh.len(assetWeightMaint);
    }
    final var depositLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (depositLimit.isPresent()) {
      i += 8;
    }
    final var totalAssetValueInitLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (totalAssetValueInitLimit.isPresent()) {
      i += 8;
    }
    final var oracleMaxAge = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (oracleMaxAge.isPresent()) {
      i += 2;
    }
    final var riskTier = _data[i++] == 0 ? null : RiskTier.read(_data, i);
    return new StakedSettingsEditConfig(oracle,
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
    i += Borsh.writeOptional(oracle, _data, i);
    i += Borsh.writeOptional(assetWeightInit, _data, i);
    i += Borsh.writeOptional(assetWeightMaint, _data, i);
    i += Borsh.writeOptional(depositLimit, _data, i);
    i += Borsh.writeOptional(totalAssetValueInitLimit, _data, i);
    i += Borsh.writeOptionalshort(oracleMaxAge, _data, i);
    i += Borsh.writeOptional(riskTier, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (oracle == null ? 1 : (1 + 32))
         + (assetWeightInit == null ? 1 : (1 + Borsh.len(assetWeightInit)))
         + (assetWeightMaint == null ? 1 : (1 + Borsh.len(assetWeightMaint)))
         + (depositLimit == null || depositLimit.isEmpty() ? 1 : (1 + 8))
         + (totalAssetValueInitLimit == null || totalAssetValueInitLimit.isEmpty() ? 1 : (1 + 8))
         + (oracleMaxAge == null || oracleMaxAge.isEmpty() ? 1 : (1 + 2))
         + (riskTier == null ? 1 : (1 + Borsh.len(riskTier)));
  }
}
