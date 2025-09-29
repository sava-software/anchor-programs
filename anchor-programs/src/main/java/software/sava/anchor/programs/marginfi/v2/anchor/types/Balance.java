package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Balance(int active,
                      PublicKey bankPk,
                      // Inherited from the bank when the position is first created and CANNOT BE CHANGED after that.
                      // Note that all balances created before the addition of this feature use `ASSET_TAG_DEFAULT`
                      int bankAssetTag,
                      byte[] pad0,
                      WrappedI80F48 assetShares,
                      WrappedI80F48 liabilityShares,
                      WrappedI80F48 emissionsOutstanding,
                      long lastUpdate,
                      long[] padding) implements Borsh {

  public static final int BYTES = 104;
  public static final int PAD_0_LEN = 6;
  public static final int PADDING_LEN = 1;

  public static Balance read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var active = _data[i] & 0xFF;
    ++i;
    final var bankPk = readPubKey(_data, i);
    i += 32;
    final var bankAssetTag = _data[i] & 0xFF;
    ++i;
    final var pad0 = new byte[6];
    i += Borsh.readArray(pad0, _data, i);
    final var assetShares = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetShares);
    final var liabilityShares = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityShares);
    final var emissionsOutstanding = WrappedI80F48.read(_data, i);
    i += Borsh.len(emissionsOutstanding);
    final var lastUpdate = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[1];
    Borsh.readArray(padding, _data, i);
    return new Balance(active,
                       bankPk,
                       bankAssetTag,
                       pad0,
                       assetShares,
                       liabilityShares,
                       emissionsOutstanding,
                       lastUpdate,
                       padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) active;
    ++i;
    bankPk.write(_data, i);
    i += 32;
    _data[i] = (byte) bankAssetTag;
    ++i;
    i += Borsh.writeArrayChecked(pad0, 6, _data, i);
    i += Borsh.write(assetShares, _data, i);
    i += Borsh.write(liabilityShares, _data, i);
    i += Borsh.write(emissionsOutstanding, _data, i);
    putInt64LE(_data, i, lastUpdate);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
