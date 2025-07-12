package software.sava.anchor.programs.raydium.launchpad.anchor.types;

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

public record VestingRecord(PublicKey _address,
                            Discriminator discriminator,
                            // Account update epoch
                            long epoch,
                            // The pool state account
                            PublicKey pool,
                            // The beneficiary of the vesting account
                            PublicKey beneficiary,
                            // The amount of tokens claimed
                            long claimedAmount,
                            // The share amount of the token to be vested
                            long tokenShareAmount,
                            // padding for future updates
                            long[] padding) implements Borsh {

  public static final int BYTES = 160;
  public static final int PADDING_LEN = 8;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(106, 243, 221, 205, 230, 126, 85, 83);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int EPOCH_OFFSET = 8;
  public static final int POOL_OFFSET = 16;
  public static final int BENEFICIARY_OFFSET = 48;
  public static final int CLAIMED_AMOUNT_OFFSET = 80;
  public static final int TOKEN_SHARE_AMOUNT_OFFSET = 88;
  public static final int PADDING_OFFSET = 96;

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createBeneficiaryFilter(final PublicKey beneficiary) {
    return Filter.createMemCompFilter(BENEFICIARY_OFFSET, beneficiary);
  }

  public static Filter createClaimedAmountFilter(final long claimedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, claimedAmount);
    return Filter.createMemCompFilter(CLAIMED_AMOUNT_OFFSET, _data);
  }

  public static Filter createTokenShareAmountFilter(final long tokenShareAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenShareAmount);
    return Filter.createMemCompFilter(TOKEN_SHARE_AMOUNT_OFFSET, _data);
  }

  public static VestingRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VestingRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VestingRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VestingRecord> FACTORY = VestingRecord::read;

  public static VestingRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var pool = readPubKey(_data, i);
    i += 32;
    final var beneficiary = readPubKey(_data, i);
    i += 32;
    final var claimedAmount = getInt64LE(_data, i);
    i += 8;
    final var tokenShareAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[8];
    Borsh.readArray(padding, _data, i);
    return new VestingRecord(_address,
                             discriminator,
                             epoch,
                             pool,
                             beneficiary,
                             claimedAmount,
                             tokenShareAmount,
                             padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, epoch);
    i += 8;
    pool.write(_data, i);
    i += 32;
    beneficiary.write(_data, i);
    i += 32;
    putInt64LE(_data, i, claimedAmount);
    i += 8;
    putInt64LE(_data, i, tokenShareAmount);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
