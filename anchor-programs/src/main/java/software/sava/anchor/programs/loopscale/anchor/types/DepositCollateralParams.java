package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Remaining accounts:
//
//num ledgers = L
//
//1. LTV Write:
//    0 -> (L-1): Ledger Market Information
//
//Asset index guidance:
//1. LTV Write:
//    0 -> (L-1): Collateral index for deposited collateral on the ledger market information
public record DepositCollateralParams(long amount,
                                      int assetType,
                                      PublicKey assetIdentifier,
                                      byte[] assetIndexGuidance) implements Borsh {

  public static DepositCollateralParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var assetType = _data[i] & 0xFF;
    ++i;
    final var assetIdentifier = readPubKey(_data, i);
    i += 32;
    final byte[] assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new DepositCollateralParams(amount,
                                       assetType,
                                       assetIdentifier,
                                       assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) assetType;
    ++i;
    assetIdentifier.write(_data, i);
    i += 32;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 1 + 32 + Borsh.lenVector(assetIndexGuidance);
  }
}
