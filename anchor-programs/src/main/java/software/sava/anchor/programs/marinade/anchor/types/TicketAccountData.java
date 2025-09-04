package software.sava.anchor.programs.marinade.anchor.types;

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

public record TicketAccountData(PublicKey _address,
                                Discriminator discriminator,
                                PublicKey stateAddress,
                                PublicKey beneficiary,
                                long lamportsAmount,
                                long createdEpoch) implements Borsh {

  public static final int BYTES = 88;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int STATE_ADDRESS_OFFSET = 8;
  public static final int BENEFICIARY_OFFSET = 40;
  public static final int LAMPORTS_AMOUNT_OFFSET = 72;
  public static final int CREATED_EPOCH_OFFSET = 80;

  public static Filter createStateAddressFilter(final PublicKey stateAddress) {
    return Filter.createMemCompFilter(STATE_ADDRESS_OFFSET, stateAddress);
  }

  public static Filter createBeneficiaryFilter(final PublicKey beneficiary) {
    return Filter.createMemCompFilter(BENEFICIARY_OFFSET, beneficiary);
  }

  public static Filter createLamportsAmountFilter(final long lamportsAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lamportsAmount);
    return Filter.createMemCompFilter(LAMPORTS_AMOUNT_OFFSET, _data);
  }

  public static Filter createCreatedEpochFilter(final long createdEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, createdEpoch);
    return Filter.createMemCompFilter(CREATED_EPOCH_OFFSET, _data);
  }

  public static TicketAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TicketAccountData read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TicketAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TicketAccountData> FACTORY = TicketAccountData::read;

  public static TicketAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var stateAddress = readPubKey(_data, i);
    i += 32;
    final var beneficiary = readPubKey(_data, i);
    i += 32;
    final var lamportsAmount = getInt64LE(_data, i);
    i += 8;
    final var createdEpoch = getInt64LE(_data, i);
    return new TicketAccountData(_address,
                                 discriminator,
                                 stateAddress,
                                 beneficiary,
                                 lamportsAmount,
                                 createdEpoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    stateAddress.write(_data, i);
    i += 32;
    beneficiary.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lamportsAmount);
    i += 8;
    putInt64LE(_data, i, createdEpoch);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
