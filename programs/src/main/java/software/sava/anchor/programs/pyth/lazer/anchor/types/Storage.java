package software.sava.anchor.programs.pyth.lazer.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Storage(PublicKey _address,
                      Discriminator discriminator,
                      PublicKey topAuthority,
                      PublicKey treasury,
                      long singleUpdateFeeInLamports,
                      int numTrustedSigners,
                      TrustedSignerInfo[] trustedSigners,
                      byte[] extraSpace) implements Borsh {

  public static final int BYTES = 381;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(209, 117, 255, 185, 196, 175, 68, 9);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int TOP_AUTHORITY_OFFSET = 8;
  public static final int TREASURY_OFFSET = 40;
  public static final int SINGLE_UPDATE_FEE_IN_LAMPORTS_OFFSET = 72;
  public static final int NUM_TRUSTED_SIGNERS_OFFSET = 80;
  public static final int TRUSTED_SIGNERS_OFFSET = 81;
  public static final int EXTRA_SPACE_OFFSET = 281;

  public static Filter createTopAuthorityFilter(final PublicKey topAuthority) {
    return Filter.createMemCompFilter(TOP_AUTHORITY_OFFSET, topAuthority);
  }

  public static Filter createTreasuryFilter(final PublicKey treasury) {
    return Filter.createMemCompFilter(TREASURY_OFFSET, treasury);
  }

  public static Filter createSingleUpdateFeeInLamportsFilter(final long singleUpdateFeeInLamports) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, singleUpdateFeeInLamports);
    return Filter.createMemCompFilter(SINGLE_UPDATE_FEE_IN_LAMPORTS_OFFSET, _data);
  }

  public static Filter createNumTrustedSignersFilter(final int numTrustedSigners) {
    return Filter.createMemCompFilter(NUM_TRUSTED_SIGNERS_OFFSET, new byte[]{(byte) numTrustedSigners});
  }

  public static Storage read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Storage read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Storage read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Storage> FACTORY = Storage::read;

  public static Storage read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var topAuthority = readPubKey(_data, i);
    i += 32;
    final var treasury = readPubKey(_data, i);
    i += 32;
    final var singleUpdateFeeInLamports = getInt64LE(_data, i);
    i += 8;
    final var numTrustedSigners = _data[i] & 0xFF;
    ++i;
    final var trustedSigners = new TrustedSignerInfo[5];
    i += Borsh.readArray(trustedSigners, TrustedSignerInfo::read, _data, i);
    final var extraSpace = new byte[100];
    Borsh.readArray(extraSpace, _data, i);
    return new Storage(_address,
                       discriminator,
                       topAuthority,
                       treasury,
                       singleUpdateFeeInLamports,
                       numTrustedSigners,
                       trustedSigners,
                       extraSpace);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    topAuthority.write(_data, i);
    i += 32;
    treasury.write(_data, i);
    i += 32;
    putInt64LE(_data, i, singleUpdateFeeInLamports);
    i += 8;
    _data[i] = (byte) numTrustedSigners;
    ++i;
    i += Borsh.writeArray(trustedSigners, _data, i);
    i += Borsh.writeArray(extraSpace, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
