package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Operator(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey signer,
                       BigInteger permission,
                       long[] padding) implements Borsh {

  public static final int BYTES = 72;
  public static final int PADDING_LEN = 2;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(219, 31, 188, 145, 69, 139, 204, 117);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int SIGNER_OFFSET = 8;
  public static final int PERMISSION_OFFSET = 40;
  public static final int PADDING_OFFSET = 56;

  public static Filter createSignerFilter(final PublicKey signer) {
    return Filter.createMemCompFilter(SIGNER_OFFSET, signer);
  }

  public static Filter createPermissionFilter(final BigInteger permission) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, permission);
    return Filter.createMemCompFilter(PERMISSION_OFFSET, _data);
  }

  public static Operator read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Operator read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Operator read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Operator> FACTORY = Operator::read;

  public static Operator read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var signer = readPubKey(_data, i);
    i += 32;
    final var permission = getInt128LE(_data, i);
    i += 16;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new Operator(_address, discriminator, signer, permission, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    signer.write(_data, i);
    i += 32;
    putInt128LE(_data, i, permission);
    i += 16;
    i += Borsh.writeArrayChecked(padding, 2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
