package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record RFQUser(PublicKey _address, Discriminator discriminator, PublicKey userPubkey, RFQOrderId[] rfqOrderData) implements Borsh {

  public static final int BYTES = 552;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int USER_PUBKEY_OFFSET = 8;
  public static final int RFQ_ORDER_DATA_OFFSET = 40;

  public static Filter createUserPubkeyFilter(final PublicKey userPubkey) {
    return Filter.createMemCompFilter(USER_PUBKEY_OFFSET, userPubkey);
  }

  public static RFQUser read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static RFQUser read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], RFQUser> FACTORY = RFQUser::read;

  public static RFQUser read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var userPubkey = readPubKey(_data, i);
    i += 32;
    final var rfqOrderData = new RFQOrderId[32];
    Borsh.readArray(rfqOrderData, RFQOrderId::read, _data, i);
    return new RFQUser(_address, discriminator, userPubkey, rfqOrderData);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    userPubkey.write(_data, i);
    i += 32;
    i += Borsh.writeArray(rfqOrderData, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
