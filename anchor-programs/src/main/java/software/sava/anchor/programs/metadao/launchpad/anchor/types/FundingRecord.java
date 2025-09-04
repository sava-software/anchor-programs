package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record FundingRecord(PublicKey _address,
                            Discriminator discriminator,
                            // The PDA bump.
                            int pdaBump,
                            // The funder.
                            PublicKey funder,
                            // The launch.
                            PublicKey launch,
                            // The amount of USDC that has been committed by the funder.
                            long committedAmount,
                            // The sequence number of this funding record. Useful for sorting events.
                            long seqNum) implements Borsh {

  public static final int BYTES = 89;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PDA_BUMP_OFFSET = 8;
  public static final int FUNDER_OFFSET = 9;
  public static final int LAUNCH_OFFSET = 41;
  public static final int COMMITTED_AMOUNT_OFFSET = 73;
  public static final int SEQ_NUM_OFFSET = 81;

  public static Filter createPdaBumpFilter(final int pdaBump) {
    return Filter.createMemCompFilter(PDA_BUMP_OFFSET, new byte[]{(byte) pdaBump});
  }

  public static Filter createFunderFilter(final PublicKey funder) {
    return Filter.createMemCompFilter(FUNDER_OFFSET, funder);
  }

  public static Filter createLaunchFilter(final PublicKey launch) {
    return Filter.createMemCompFilter(LAUNCH_OFFSET, launch);
  }

  public static Filter createCommittedAmountFilter(final long committedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, committedAmount);
    return Filter.createMemCompFilter(COMMITTED_AMOUNT_OFFSET, _data);
  }

  public static Filter createSeqNumFilter(final long seqNum) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, seqNum);
    return Filter.createMemCompFilter(SEQ_NUM_OFFSET, _data);
  }

  public static FundingRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FundingRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FundingRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FundingRecord> FACTORY = FundingRecord::read;

  public static FundingRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var funder = readPubKey(_data, i);
    i += 32;
    final var launch = readPubKey(_data, i);
    i += 32;
    final var committedAmount = getInt64LE(_data, i);
    i += 8;
    final var seqNum = getInt64LE(_data, i);
    return new FundingRecord(_address,
                             discriminator,
                             pdaBump,
                             funder,
                             launch,
                             committedAmount,
                             seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) pdaBump;
    ++i;
    funder.write(_data, i);
    i += 32;
    launch.write(_data, i);
    i += 32;
    putInt64LE(_data, i, committedAmount);
    i += 8;
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
