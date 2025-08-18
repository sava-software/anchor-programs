package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.Boolean;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateAssetDataParams(PublicKey assetIdentifier,
                                    PublicKey quoteMint,
                                    PublicKey oracleAccount,
                                    OptionalInt oracleType,
                                    OptionalInt maxUncertainty,
                                    OptionalInt maxAge,
                                    OptionalInt ltv,
                                    OptionalInt liquidationThreshold,
                                    OptionalLong maxCollateralAllocationPct,
                                    Boolean remove) implements Borsh {

  public static UpdateAssetDataParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetIdentifier = readPubKey(_data, i);
    i += 32;
    final var quoteMint = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (quoteMint != null) {
      i += 32;
    }
    final var oracleAccount = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (oracleAccount != null) {
      i += 32;
    }
    final var oracleType = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (oracleType.isPresent()) {
      ++i;
    }
    final var maxUncertainty = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (maxUncertainty.isPresent()) {
      i += 4;
    }
    final var maxAge = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (maxAge.isPresent()) {
      i += 2;
    }
    final var ltv = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (ltv.isPresent()) {
      i += 4;
    }
    final var liquidationThreshold = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (liquidationThreshold.isPresent()) {
      i += 4;
    }
    final var maxCollateralAllocationPct = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxCollateralAllocationPct.isPresent()) {
      i += 8;
    }
    final var remove = _data[i++] == 0 ? null : _data[i] == 1;
    return new UpdateAssetDataParams(assetIdentifier,
                                     quoteMint,
                                     oracleAccount,
                                     oracleType,
                                     maxUncertainty,
                                     maxAge,
                                     ltv,
                                     liquidationThreshold,
                                     maxCollateralAllocationPct,
                                     remove);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    assetIdentifier.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(quoteMint, _data, i);
    i += Borsh.writeOptional(oracleAccount, _data, i);
    i += Borsh.writeOptionalbyte(oracleType, _data, i);
    i += Borsh.writeOptional(maxUncertainty, _data, i);
    i += Borsh.writeOptionalshort(maxAge, _data, i);
    i += Borsh.writeOptional(ltv, _data, i);
    i += Borsh.writeOptional(liquidationThreshold, _data, i);
    i += Borsh.writeOptional(maxCollateralAllocationPct, _data, i);
    i += Borsh.writeOptional(remove, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + (quoteMint == null ? 1 : (1 + 32))
         + (oracleAccount == null ? 1 : (1 + 32))
         + (oracleType == null || oracleType.isEmpty() ? 1 : (1 + 1))
         + (maxUncertainty == null || maxUncertainty.isEmpty() ? 1 : (1 + 4))
         + (maxAge == null || maxAge.isEmpty() ? 1 : (1 + 2))
         + (ltv == null || ltv.isEmpty() ? 1 : (1 + 4))
         + (liquidationThreshold == null || liquidationThreshold.isEmpty() ? 1 : (1 + 4))
         + (maxCollateralAllocationPct == null || maxCollateralAllocationPct.isEmpty() ? 1 : (1 + 8))
         + (remove == null ? 1 : (1 + 1));
  }
}
