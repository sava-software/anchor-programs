package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

// * This struct is a duplicate of SignedMsgUserOrdersZeroCopy
// * It is used to give anchor an struct to generate the idl for clients
// * The struct SignedMsgUserOrdersZeroCopy is used to load the data in efficiently
public record SignedMsgUserOrders(PublicKey _address,
                                  Discriminator discriminator,
                                  PublicKey authorityPubkey,
                                  int padding,
                                  SignedMsgOrderId[] signedMsgOrderData) implements Borsh {

  public static final int AUTHORITY_PUBKEY_OFFSET = 8;
  public static final int PADDING_OFFSET = 40;
  public static final int SIGNED_MSG_ORDER_DATA_OFFSET = 44;

  public static Filter createAuthorityPubkeyFilter(final PublicKey authorityPubkey) {
    return Filter.createMemCompFilter(AUTHORITY_PUBKEY_OFFSET, authorityPubkey);
  }

  public static Filter createPaddingFilter(final int padding) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding);
    return Filter.createMemCompFilter(PADDING_OFFSET, _data);
  }

  public static SignedMsgUserOrders read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static SignedMsgUserOrders read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static SignedMsgUserOrders read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], SignedMsgUserOrders> FACTORY = SignedMsgUserOrders::read;

  public static SignedMsgUserOrders read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authorityPubkey = readPubKey(_data, i);
    i += 32;
    final var padding = getInt32LE(_data, i);
    i += 4;
    final var signedMsgOrderData = Borsh.readVector(SignedMsgOrderId.class, SignedMsgOrderId::read, _data, i);
    return new SignedMsgUserOrders(_address, discriminator, authorityPubkey, padding, signedMsgOrderData);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authorityPubkey.write(_data, i);
    i += 32;
    putInt32LE(_data, i, padding);
    i += 4;
    i += Borsh.writeVector(signedMsgOrderData, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + 4 + Borsh.lenVector(signedMsgOrderData);
  }
}
