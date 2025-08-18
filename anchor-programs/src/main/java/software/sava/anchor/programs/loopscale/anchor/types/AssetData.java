package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record AssetData(PublicKey assetIdentifier,
                        PublicKey quoteMint,
                        PublicKey oracleAccount,
                        int oracleType,
                        PodU32CBPS maxUncertainty,
                        PodU16 maxAge,
                        int decimals,
                        PodU32CBPS ltv,
                        PodU32CBPS liquidationThreshold,
                        CollateralCaps collateralCaps) implements Borsh {

  public static final int BYTES = 128;

  public static AssetData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetIdentifier = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var oracleAccount = readPubKey(_data, i);
    i += 32;
    final var oracleType = _data[i] & 0xFF;
    ++i;
    final var maxUncertainty = PodU32CBPS.read(_data, i);
    i += Borsh.len(maxUncertainty);
    final var maxAge = PodU16.read(_data, i);
    i += Borsh.len(maxAge);
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var ltv = PodU32CBPS.read(_data, i);
    i += Borsh.len(ltv);
    final var liquidationThreshold = PodU32CBPS.read(_data, i);
    i += Borsh.len(liquidationThreshold);
    final var collateralCaps = CollateralCaps.read(_data, i);
    return new AssetData(assetIdentifier,
                         quoteMint,
                         oracleAccount,
                         oracleType,
                         maxUncertainty,
                         maxAge,
                         decimals,
                         ltv,
                         liquidationThreshold,
                         collateralCaps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    assetIdentifier.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    oracleAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) oracleType;
    ++i;
    i += Borsh.write(maxUncertainty, _data, i);
    i += Borsh.write(maxAge, _data, i);
    _data[i] = (byte) decimals;
    ++i;
    i += Borsh.write(ltv, _data, i);
    i += Borsh.write(liquidationThreshold, _data, i);
    i += Borsh.write(collateralCaps, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
