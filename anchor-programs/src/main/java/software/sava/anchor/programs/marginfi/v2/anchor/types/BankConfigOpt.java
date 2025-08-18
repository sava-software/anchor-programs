package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.lang.Boolean;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record BankConfigOpt(WrappedI80F48 assetWeightInit,
                            WrappedI80F48 assetWeightMaint,
                            WrappedI80F48 liabilityWeightInit,
                            WrappedI80F48 liabilityWeightMaint,
                            OptionalLong depositLimit,
                            OptionalLong borrowLimit,
                            BankOperationalState operationalState,
                            InterestRateConfigOpt interestRateConfig,
                            RiskTier riskTier,
                            OptionalInt assetTag,
                            OptionalLong totalAssetValueInitLimit,
                            OptionalInt oracleMaxConfidence,
                            OptionalInt oracleMaxAge,
                            Boolean permissionlessBadDebtSettlement,
                            Boolean freezeSettings) implements Borsh {

  public static BankConfigOpt read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetWeightInit = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (assetWeightInit != null) {
      i += Borsh.len(assetWeightInit);
    }
    final var assetWeightMaint = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (assetWeightMaint != null) {
      i += Borsh.len(assetWeightMaint);
    }
    final var liabilityWeightInit = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (liabilityWeightInit != null) {
      i += Borsh.len(liabilityWeightInit);
    }
    final var liabilityWeightMaint = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (liabilityWeightMaint != null) {
      i += Borsh.len(liabilityWeightMaint);
    }
    final var depositLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (depositLimit.isPresent()) {
      i += 8;
    }
    final var borrowLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (borrowLimit.isPresent()) {
      i += 8;
    }
    final var operationalState = _data[i++] == 0 ? null : BankOperationalState.read(_data, i);
    if (operationalState != null) {
      i += Borsh.len(operationalState);
    }
    final var interestRateConfig = _data[i++] == 0 ? null : InterestRateConfigOpt.read(_data, i);
    if (interestRateConfig != null) {
      i += Borsh.len(interestRateConfig);
    }
    final var riskTier = _data[i++] == 0 ? null : RiskTier.read(_data, i);
    if (riskTier != null) {
      i += Borsh.len(riskTier);
    }
    final var assetTag = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (assetTag.isPresent()) {
      ++i;
    }
    final var totalAssetValueInitLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (totalAssetValueInitLimit.isPresent()) {
      i += 8;
    }
    final var oracleMaxConfidence = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (oracleMaxConfidence.isPresent()) {
      i += 4;
    }
    final var oracleMaxAge = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (oracleMaxAge.isPresent()) {
      i += 2;
    }
    final var permissionlessBadDebtSettlement = _data[i++] == 0 ? null : _data[i] == 1;
    if (permissionlessBadDebtSettlement != null) {
      ++i;
    }
    final var freezeSettings = _data[i++] == 0 ? null : _data[i] == 1;
    return new BankConfigOpt(assetWeightInit,
                             assetWeightMaint,
                             liabilityWeightInit,
                             liabilityWeightMaint,
                             depositLimit,
                             borrowLimit,
                             operationalState,
                             interestRateConfig,
                             riskTier,
                             assetTag,
                             totalAssetValueInitLimit,
                             oracleMaxConfidence,
                             oracleMaxAge,
                             permissionlessBadDebtSettlement,
                             freezeSettings);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(assetWeightInit, _data, i);
    i += Borsh.writeOptional(assetWeightMaint, _data, i);
    i += Borsh.writeOptional(liabilityWeightInit, _data, i);
    i += Borsh.writeOptional(liabilityWeightMaint, _data, i);
    i += Borsh.writeOptional(depositLimit, _data, i);
    i += Borsh.writeOptional(borrowLimit, _data, i);
    i += Borsh.writeOptional(operationalState, _data, i);
    i += Borsh.writeOptional(interestRateConfig, _data, i);
    i += Borsh.writeOptional(riskTier, _data, i);
    i += Borsh.writeOptionalbyte(assetTag, _data, i);
    i += Borsh.writeOptional(totalAssetValueInitLimit, _data, i);
    i += Borsh.writeOptional(oracleMaxConfidence, _data, i);
    i += Borsh.writeOptionalshort(oracleMaxAge, _data, i);
    i += Borsh.writeOptional(permissionlessBadDebtSettlement, _data, i);
    i += Borsh.writeOptional(freezeSettings, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (assetWeightInit == null ? 1 : (1 + Borsh.len(assetWeightInit)))
         + (assetWeightMaint == null ? 1 : (1 + Borsh.len(assetWeightMaint)))
         + (liabilityWeightInit == null ? 1 : (1 + Borsh.len(liabilityWeightInit)))
         + (liabilityWeightMaint == null ? 1 : (1 + Borsh.len(liabilityWeightMaint)))
         + (depositLimit == null || depositLimit.isEmpty() ? 1 : (1 + 8))
         + (borrowLimit == null || borrowLimit.isEmpty() ? 1 : (1 + 8))
         + (operationalState == null ? 1 : (1 + Borsh.len(operationalState)))
         + (interestRateConfig == null ? 1 : (1 + Borsh.len(interestRateConfig)))
         + (riskTier == null ? 1 : (1 + Borsh.len(riskTier)))
         + (assetTag == null || assetTag.isEmpty() ? 1 : (1 + 1))
         + (totalAssetValueInitLimit == null || totalAssetValueInitLimit.isEmpty() ? 1 : (1 + 8))
         + (oracleMaxConfidence == null || oracleMaxConfidence.isEmpty() ? 1 : (1 + 4))
         + (oracleMaxAge == null || oracleMaxAge.isEmpty() ? 1 : (1 + 2))
         + (permissionlessBadDebtSettlement == null ? 1 : (1 + 1))
         + (freezeSettings == null ? 1 : (1 + 1));
  }
}
