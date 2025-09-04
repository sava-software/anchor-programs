package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// Referrer account -> each owner can have multiple accounts for specific reserves
public record ReferrerTokenState(PublicKey _address,
                                 Discriminator discriminator,
                                 // Pubkey of the referrer/owner
                                 PublicKey referrer,
                                 // Token mint for the account
                                 PublicKey mint,
                                 // Amount that has been accumulated and not claimed yet -> available to claim (scaled fraction)
                                 BigInteger amountUnclaimedSf,
                                 // Amount that has been accumulated in total -> both already claimed and unclaimed (scaled fraction)
                                 BigInteger amountCumulativeSf,
                                 // Referrer token state bump, used for address validation
                                 long bump,
                                 long[] padding) implements Borsh {

  public static final int BYTES = 360;
  public static final int PADDING_LEN = 31;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int REFERRER_OFFSET = 8;
  public static final int MINT_OFFSET = 40;
  public static final int AMOUNT_UNCLAIMED_SF_OFFSET = 72;
  public static final int AMOUNT_CUMULATIVE_SF_OFFSET = 88;
  public static final int BUMP_OFFSET = 104;
  public static final int PADDING_OFFSET = 112;

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createAmountUnclaimedSfFilter(final BigInteger amountUnclaimedSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, amountUnclaimedSf);
    return Filter.createMemCompFilter(AMOUNT_UNCLAIMED_SF_OFFSET, _data);
  }

  public static Filter createAmountCumulativeSfFilter(final BigInteger amountCumulativeSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, amountCumulativeSf);
    return Filter.createMemCompFilter(AMOUNT_CUMULATIVE_SF_OFFSET, _data);
  }

  public static Filter createBumpFilter(final long bump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, bump);
    return Filter.createMemCompFilter(BUMP_OFFSET, _data);
  }

  public static ReferrerTokenState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ReferrerTokenState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ReferrerTokenState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ReferrerTokenState> FACTORY = ReferrerTokenState::read;

  public static ReferrerTokenState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var amountUnclaimedSf = getInt128LE(_data, i);
    i += 16;
    final var amountCumulativeSf = getInt128LE(_data, i);
    i += 16;
    final var bump = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[31];
    Borsh.readArray(padding, _data, i);
    return new ReferrerTokenState(_address,
                                  discriminator,
                                  referrer,
                                  mint,
                                  amountUnclaimedSf,
                                  amountCumulativeSf,
                                  bump,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    referrer.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putInt128LE(_data, i, amountUnclaimedSf);
    i += 16;
    putInt128LE(_data, i, amountCumulativeSf);
    i += 16;
    putInt64LE(_data, i, bump);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
